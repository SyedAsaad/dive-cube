package com.demo.dive.cube.service;

import com.demo.dive.cube.config.Queries;
import com.demo.dive.cube.config.ReportConstants;
import com.demo.dive.cube.config.UtilService;
import com.demo.dive.cube.config.exception.BadRequestException;
import com.demo.dive.cube.dto.StudentDto;
import com.demo.dive.cube.jrbeans.DetailOrderFormJrBean;
import com.demo.dive.cube.jrbeans.StudentCourseJrBean;
import com.demo.dive.cube.model.Student;
import com.demo.dive.cube.repository.StudentRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
//                String fileName = student.getImageName().substring(student.getImageName().indexOf("-")+1,student.getImageName().length());
//                MultipartFile multipartFile = new MockMultipartFile(fileName,student.getImageName(),"image/jpeg", IOUtils.toByteArray(fileLocation + student.getImageName()));
//                studentDto.setImage(multipartFile);
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

    public void studentContact(HttpServletRequest request, HttpServletResponse response) {
        String criteria = "";
        int parameterNo = 1;
        Map<Integer, Object> parameters = new LinkedHashMap<>();
        String dob = request.getParameter("dob");
        String firstName = request.getParameter("firstName");
        String middelName = request.getParameter("middelName");
        String lastName = request.getParameter("lastName");
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

        Query query = entityManager.createNativeQuery(Queries.studentDetailQuery.replace("criteria", criteria));

        List<Object> results = UtilService.toParameterized(query, parameters, parameterNo).getResultList();

        List<StudentCourseJrBean> data  =  getStudentReportBean(results);
        reportService.export(data, ReportConstants.STUDENT_CONTACT_REPORT, request, response);


    }

   private List<StudentCourseJrBean> getStudentReportBean(List<Object> results){
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
            studentCourseJrBean.setCourseCompletionDate(UtilService.isValid(obj[i++]));
            studentCourseJrBean.setDateOfProcessing(UtilService.isValid(obj[i++]));
            data.add(studentCourseJrBean);
        }

        return data;
    }

    public void oldStudent(HttpServletRequest request, HttpServletResponse response) {
    }
}
