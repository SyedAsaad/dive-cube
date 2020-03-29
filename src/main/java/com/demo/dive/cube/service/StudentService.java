package com.demo.dive.cube.service;

import com.demo.dive.cube.config.UtilService;
import com.demo.dive.cube.config.exception.BadRequestException;
import com.demo.dive.cube.dto.StudentDto;
import com.demo.dive.cube.model.Student;
import com.demo.dive.cube.repository.StudentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Value("${student.file.path}")
    private String fileLocation;

    public List<Student> findAllStudents(){
        return studentRepository.findAllByIsDeletedFalse();
    }
/*    public Student findStudentById(Long id){
        if(id!=null)
            return studentRepository.findByIdAndIsDeletedFalse(id);
        else
            return null;
    }*/
    /**
     * upload student image to server
     * @param file
     * @param fileName
     * @return
     */
    private String uploadStudentImage(MultipartFile file, String fileName){
        String fullFileName = fileName ;
        Boolean isFileUploaded = UtilService.uploadFile(file, fileLocation, fullFileName);
        if(!isFileUploaded) throw new BadRequestException("file.upload.error");
        return fullFileName;
    }

    /**
     * delete student image
     * @param fileName
     */
    private void deleteStudentImage(String fileName){
        String fullFileName = fileName;
        UtilService.deleteFileIfExist(fileLocation, fullFileName);
    }

    public ModelAndView saveNUpdateStudent(StudentDto studentDto, ModelAndView modelAndView) {
        String fileName = "";
        Student existStudent = new Student();
        try {
            modelAndView.addObject("studentDto", new StudentDto());
            if (studentDto != null && studentDto.getImage() != null) {
                if (UtilService.isValidFile(studentDto.getImage(), 2000L, 1786000L,
                        new ArrayList<String>() {{
                            add("JPEG");
                            add("JPG");
                        }})) {
                    if (studentDto.getId() != null)
                        existStudent = studentRepository.findByIdAndIsDeletedFalse(studentDto.getId());
                    if (existStudent != null) {
                        getStudentData(existStudent, studentDto);
                        fileName = existStudent.getImageName() != null ? existStudent.getImageName() : System.currentTimeMillis() + "-" + studentDto.getImage().getOriginalFilename();
                        String filePath = uploadStudentImage(studentDto.getImage(), fileName);
                        existStudent.setImageName(filePath);
                        studentRepository.save(existStudent);

                    }

                } else {
                    modelAndView.addObject("studentDto", studentDto);
                    modelAndView.addObject("fileError", true);
                }
            }
        } catch (Exception e) {
            deleteStudentImage(fileName);
        }
        modelAndView.addObject("studentList",findAllStudents());
        return modelAndView;
    }

    private void getStudentData(Student existStudent, StudentDto studentDto) {
        try {
            existStudent.setName(studentDto.getName());
            existStudent.setGender(studentDto.getGender());
            existStudent.setCountry(studentDto.getCountry());
            existStudent.setDob(studentDto.getDob());
            existStudent.setPermanentAddress(studentDto.getPermanentAddress());
            existStudent.setCity(studentDto.getCity());
            existStudent.setEmergencyContactName(studentDto.getEmergencyContactName());
            existStudent.setEmergencyContactNum(studentDto.getEmergencyContactNum());
            existStudent.setPhoneNumber(studentDto.getPhoneNumber());
            existStudent.setRecievingAddress(studentDto.getRecievingAddress());
            existStudent.setZipCode(studentDto.getZipCode());
        }catch (Exception e){}
    }


    public void deleteStudent(Long id){
        Student student = studentRepository.findByIdAndIsDeletedFalse(id);
        student.setIsDeleted(true);
        deleteStudentImage(student.getImageName());
        studentRepository.save(student);

    }

    public StudentDto findStudentById(Long id) {
        StudentDto studentDto = new StudentDto();
        try{
            if (id != null) {
                Student student = studentRepository.findByIdAndIsDeletedFalse(id);

                studentDto.setId(student.getId());
                String fileName = student.getImageName().substring(student.getImageName().indexOf("-")+1,student.getImageName().length());
                MultipartFile multipartFile = new MockMultipartFile(fileName,student.getImageName(),"image/jpeg", new FileInputStream(fileLocation+student.getImageName()));
                studentDto.setImage(multipartFile);
                BeanUtils.copyProperties(student, studentDto);
                return studentDto;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return studentDto;
    }

    public String getImageName(Long id){
        return studentRepository.findByIdAndIsDeletedFalse(id).getImageName();
    }
}
