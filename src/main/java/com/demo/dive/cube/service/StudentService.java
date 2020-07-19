package com.demo.dive.cube.service;

import com.demo.dive.cube.config.Queries;
import com.demo.dive.cube.config.ReportConstants;
import com.demo.dive.cube.config.UtilService;
import com.demo.dive.cube.config.exception.BadRequestException;
import com.demo.dive.cube.dto.StudentDto;
import com.demo.dive.cube.jrbeans.BeachLogJrBean;
import com.demo.dive.cube.jrbeans.StudentCountJrBean;
import com.demo.dive.cube.jrbeans.StudentCourseJrBean;
import com.demo.dive.cube.model.Student;
import com.demo.dive.cube.repository.StudentRepository;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.DateFormatter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Value("${student.file.path}")
    private String fileLocation;

    @PersistenceContext
    public EntityManager entityManager;

    @Autowired
    private ReportService reportService;

    private static final String[] columnName = { "First Name","Middle Name","Last Name"
            ,"BirthDate","City","State","Country","Address","Zip code","Telephone"
            ,"Emergency Telephone","email","Enrollment Date"};

    public List<Student> findAllStudents() {
        return studentRepository.findAllByIsDeletedFalse();
    }

    /**
     * upload student image to server
     *
     * @param file
     * @param fileName
     * @return
     */
    private String uploadStudentImage(MultipartFile file, String fileName) {
        String fullFileName = fileName;
        Boolean isFileUploaded = UtilService.uploadFile(file, fileLocation, fullFileName);
        if (!isFileUploaded) throw new BadRequestException("file.upload.error");
        return fullFileName;
    }

    /**
     * delete student image
     *
     * @param fileName
     */
    private void deleteStudentImage(String fileName) {
        String fullFileName = fileName;
        UtilService.deleteFileIfExist(fileLocation, fullFileName);
    }

    public ModelAndView saveNUpdateStudent(StudentDto studentDto, ModelAndView modelAndView) {
        String fileName = "";
        Student existStudent = new Student();
        try {
            modelAndView.addObject("studentDto", new StudentDto());
            if (studentDto != null && studentDto.getImage() != null) {
                if (studentDto.getId() != null)
                    existStudent = studentRepository.findByIdAndIsDeletedFalse(studentDto.getId());
                if (existStudent != null) {
                    BeanUtils.copyProperties(studentDto, existStudent);
                    if (!(studentDto.getId() != null && studentDto.getImage().getOriginalFilename().isEmpty())) {
                        fileName = existStudent.getImageName() != null ? existStudent.getImageName() : System.currentTimeMillis() + "-" + studentDto.getImage().getOriginalFilename();
                        String filePath = uploadStudentImage(studentDto.getImage(), fileName);
                        existStudent.setImageName(filePath);
                    }
                    studentRepository.save(existStudent);

                }

            }
        } catch (Exception e) {
            deleteStudentImage(fileName);
        }
        modelAndView.addObject("studentList", findAllStudents());
        return modelAndView;
    }

    public void deleteStudent(Long id) {
        Student student = studentRepository.findByIdAndIsDeletedFalse(id);
        student.setIsDeleted(true);
        deleteStudentImage(student.getImageName());
        studentRepository.save(student);

    }

    public StudentDto findStudentById(Long id) {
        StudentDto studentDto = new StudentDto();
        try {
            if (id != null) {
                Student student = studentRepository.findByIdAndIsDeletedFalse(id);

                studentDto.setId(student.getId());
                BeanUtils.copyProperties(student, studentDto);
                return studentDto;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentDto;
    }

    public String getImageName(Long id) {
        return studentRepository.findByIdAndIsDeletedFalse(id).getImageName();
    }

    public void studentReport(HttpServletRequest request, HttpServletResponse response) {
        String criteria = "";
        int parameterNo = 1;
        Query query = null;
        List<Object> results =  new ArrayList<>();
        Map<Integer, Object> parameters = new LinkedHashMap<>();
        String dob = request.getParameter("dob");
        String firstName = request.getParameter("firstName");
        String middelName = request.getParameter("middelName");
        String lastName = request.getParameter("lastName");
        Boolean allStudents = request.getParameter("allStudent")==null? Boolean.FALSE:Boolean.TRUE;

        if(allStudents.equals(Boolean.FALSE)) {
            if (dob != null && !dob.toString().isEmpty()) {
                criteria += " AND STR_TO_DATE(a.date_of_birth,'%d-%m-%Y') = STR_TO_DATE( ? ,'%d-%m-%Y')";
                parameters.put(parameterNo, dob);
                parameterNo++;
            }
            if (firstName != null && !firstName.isEmpty()) {
                criteria += "AND a.first_name LIKE ?";
                parameters.put(parameterNo, "%" + firstName + "%");
                parameterNo++;
            }
            if (middelName != null && !middelName.isEmpty()) {
                criteria += "AND a.middle_name LIKE ?";
                parameters.put(parameterNo, "%" + middelName + "%");
                parameterNo++;
            }
            if (lastName != null && !lastName.isEmpty()) {
                criteria += "AND a.last_name LIKE ?";
                parameters.put(parameterNo, "%" + lastName + "%");
                parameterNo++;
            }
            if(request.getParameter("fromDate") != null && !request.getParameter("fromDate").toString().isEmpty() && request.getParameter("toDate") != null && !request.getParameter("toDate").toString().isEmpty()){
                criteria += " AND STR_TO_DATE(c.course_date,'%d-%m-%Y') >= STR_TO_DATE( ? ,'%d-%m-%Y')";
                parameters.put(parameterNo,request.getParameter("fromDate"));
                parameterNo++;
                criteria += " AND STR_TO_DATE(c.course_date,'%d-%m-%Y') <= STR_TO_DATE( ? ,'%d-%m-%Y')";
                parameters.put(parameterNo,request.getParameter("toDate"));
                parameterNo++;
            }
        }

        String reportType = request.getParameter("reportType");
        if(reportType.equals("Contact_Report")) {
            query = entityManager.createNativeQuery(Queries.studentDetailQuery.replace("criteria", criteria));
            results = UtilService.toParameterized(query, parameters, parameterNo).getResultList();
            studentContact(request,response,results);
        }
        else if(reportType.equals("Old_Report")) {
            query = entityManager.createNativeQuery(Queries.oldStudentDetailQuery.replace("criteria", criteria));
            results = UtilService.toParameterized(query, parameters, parameterNo).getResultList();
            oldStudent(request,response,results);
        }
        else if(reportType.equals("Current_Report")) {
            query = entityManager.createNativeQuery(Queries.currentStudentQuery.replace("criteria", criteria));
            results = UtilService.toParameterized(query, parameters, parameterNo).getResultList();
            currentStudent(request,response,results);
        }
    }


    public void studentContact(HttpServletRequest request, HttpServletResponse response,List<Object> results ) {

        List<StudentCourseJrBean> data  =  getStudentReportBean(results);
        reportService.export(data, ReportConstants.STUDENT_CONTACT_REPORT, request, response);
    }
    public void oldStudent(HttpServletRequest request, HttpServletResponse response,List<Object> results) {

        List<StudentCourseJrBean> data  =  getStudentReportBean(results);
        reportService.export(data, ReportConstants.OLD_STUDENT_REPORT, request, response);
    }


    private void currentStudent(HttpServletRequest request, HttpServletResponse response, List<Object> results) {
        List<StudentCourseJrBean> data  =  getStudentReportBean(results);
        reportService.export(data, ReportConstants.CURRENT_STUDENT_REPORT, request, response);
    }

   private List<StudentCourseJrBean> getStudentReportBean(List<Object> results) {
       List<StudentCourseJrBean> data = new ArrayList<>();
       for (Object object : results) {
           int i = 0;
           Object[] obj = (Object[]) object;
           StudentCourseJrBean studentCourseJrBean = new StudentCourseJrBean();
           studentCourseJrBean.setFirstName(UtilService.isValid(obj[i++]));
           studentCourseJrBean.setMiddleName(UtilService.isValid(obj[i++]));
           studentCourseJrBean.setLastName(UtilService.isValid(obj[i++]));
           studentCourseJrBean.setEmail(UtilService.isValid(obj[i++]));
           studentCourseJrBean.setPermanentAddress(UtilService.isValid(obj[i++]));
           studentCourseJrBean.setPhoneNumber(UtilService.isValid(obj[i++]));
           studentCourseJrBean.setAltPhoneNumber(UtilService.isValid(obj[i++]));
           studentCourseJrBean.setCountry(UtilService.isValid(obj[i++]));
           studentCourseJrBean.setZipCode(UtilService.isValid(obj[i++]));
           studentCourseJrBean.setDob(UtilService.isValid(obj[i++]));
           studentCourseJrBean.setCourseName(UtilService.isValid(obj[i++]));
           studentCourseJrBean.setCourseDate(UtilService.isValid(obj[i++]));
           studentCourseJrBean.setCourseCompletionDate(UtilService.isValid(obj.length>=i?"":obj[i++]));
           studentCourseJrBean.setDateOfProcessing(UtilService.isValid(obj.length>=i?"":obj[i++]));
           studentCourseJrBean.setStudentName(studentCourseJrBean.getFirstName()+" "+studentCourseJrBean.getMiddleName()+" "+studentCourseJrBean.getLastName());
           data.add(studentCourseJrBean);
       }

       return data;
   }

    public void studentCountReport(HttpServletRequest request, HttpServletResponse response) {
        String criteria = "";
        int parameterNo = 1;

        Map<Integer, Object> parameters = new LinkedHashMap<>();
        Boolean allCourses = request.getParameter("allCourse")==null? Boolean.FALSE:Boolean.TRUE;
        if(allCourses.equals(Boolean.FALSE)) {
            if (request.getParameter("fromDate") != null && !request.getParameter("fromDate").toString().isEmpty() && request.getParameter("toDate") != null && !request.getParameter("toDate").toString().isEmpty()) {
                criteria += " AND STR_TO_DATE(a.course_completion_date,'%d-%m-%Y') >= STR_TO_DATE( ? ,'%d-%m-%Y')";
                parameters.put(parameterNo, request.getParameter("fromDate"));
                parameterNo++;
                criteria += " AND STR_TO_DATE(a.course_completion_date,'%d-%m-%Y') <= STR_TO_DATE( ? ,'%d-%m-%Y')";
                parameters.put(parameterNo, request.getParameter("toDate"));
                parameterNo++;
            }
        }

        Query query = entityManager.createNativeQuery(Queries.studentCountQuery.replace("criteria", criteria));
        List<Object> results  = UtilService.toParameterized(query, parameters, parameterNo).getResultList();


        List<StudentCountJrBean> data = new ArrayList<>();
        for (Object object : results) {
            int i = 0;
            Object[] obj = (Object[]) object;
            StudentCountJrBean studentCourseJrBean = new StudentCountJrBean();
            studentCourseJrBean.setStudentCount(((BigInteger)obj[i++]).toString());
            studentCourseJrBean.setCourseName(UtilService.isValid(obj[i++]));
            data.add(studentCourseJrBean);
        }

        reportService.export(data, ReportConstants.COUNT_STUDENT_REPORT, request, response);
    }

    public ByteArrayInputStream excelReport(HttpServletRequest request, HttpServletResponse response) {

        String firstName = request.getParameter("firstName");
        String middelName = request.getParameter("middelName");
        String lastName = request.getParameter("lastName");

        List<Student> studentList = studentRepository.findByFirstNameOrMiddleNameOrLastNameAndIsDeletedFalse(firstName,middelName,lastName);
        return exportExcel(studentList,"student");

    }


    public ByteArrayInputStream exportExcel(List<Student> data, String sheetname) {
        try {


            Workbook workbook = new XSSFWorkbook();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Sheet sheet = UtilService.initializeExcel(workbook,columnName,sheetname);

            // Create Other rows and cells with contacts data
            int rowCount = 1;
            for (Student student : data) {
                Row row = sheet.createRow(rowCount++);
                int columnCount = 0;
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                row.createCell(columnCount++).setCellValue(student.getFirstName());
                row.createCell(columnCount++).setCellValue(student.getMiddleName());
                row.createCell(columnCount++).setCellValue(student.getLastName());
                row.createCell(columnCount++).setCellValue(student.getDob());
                row.createCell(columnCount++).setCellValue(student.getCity());
                row.createCell(columnCount++).setCellValue(student.getState());
                row.createCell(columnCount++).setCellValue(student.getCountry());
                row.createCell(columnCount++).setCellValue(student.getPermanentAddress());
                row.createCell(columnCount++).setCellValue(student.getZipCode());
                row.createCell(columnCount++).setCellValue(student.getPhoneNumber());
                row.createCell(columnCount++).setCellValue(student.getEmergencyContactNum());
                row.createCell(columnCount++).setCellValue(student.getEmail());
                row.createCell(columnCount++).setCellValue(dateFormat.format(student.getCreationDate()));

            }

            for (int i = 0; i < columnName.length; i++) {
                sheet.autoSizeColumn(i);
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }

    }
}
