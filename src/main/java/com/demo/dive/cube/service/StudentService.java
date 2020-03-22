package com.demo.dive.cube.service;

import com.demo.dive.cube.config.UtilService;
import com.demo.dive.cube.config.exception.BadRequestException;
import com.demo.dive.cube.dto.StudentDto;
import com.demo.dive.cube.model.Student;
import com.demo.dive.cube.repository.StudentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
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
    public Student findStudentById(Long id){
        if(id!=null)
            return studentRepository.findByIdAndIsDeletedFalse(id);
        else
            return null;
    }
    /**
     * upload student image to server
     * @param file
     * @param fileName
     * @return
     */
    private String uploadStudentImage(MultipartFile file, String fileName){
        String fullFileName = fileName + ".jpg";
        Boolean isFileUploaded = UtilService.uploadFile(file, fileLocation, fullFileName);
        if(!isFileUploaded) throw new BadRequestException("file.upload.error");
        return fullFileName;
    }

    /**
     * delete student image
     * @param fileName
     */
    private void deleteStudentImage(String fileName){
        String fullFileName = fileName + ".jpg";
        UtilService.deleteFileIfExist(fileLocation, fullFileName);
    }

    public ModelAndView saveNUpdateStudent(@Valid Student student, MultipartFile image, ModelAndView modelAndView) {
        String fileName = "";
        try {

        if(student != null && image !=null){
            if(UtilService.isValidFile(image,2000L,1786000L,
                    new ArrayList<String>(){{add("JPEG");add("JPG");}})){
                if(student.getId()!=null) {
                    Student existStudent = studentRepository.findByIdAndIsDeletedFalse(student.getId());
//                    if(existStudent==null) return modelAndView.addObject("errorList","Student doesnot Exist");

                }else{

                    student.setStudentId(generateStudentId());

                }
                fileName = "STD" + "_" + student.getStudentId();
                String filePath = uploadStudentImage(image, fileName);
                student.setImageName(filePath);
                studentRepository.save(student);
            }
            else{
//                StudentDto studentDto = new StudentDto();
//                BeanUtils.copyProperties(student,studentDto);
//                modelAndView.addObject("student", student);
//                modelAndView.addObject("fileError",true);
            }
        }
    }catch (Exception e){
        deleteStudentImage(fileName);
//        modelAndView.addObject("errorList","Some Error Occured");
    }

        return modelAndView;
    }

    private String generateStudentId() {
        String studentId;
        Long id= studentRepository.getLatestId();
        if (id == null || id == 0L) {
            studentId = "0000" + 1L;
        }
        else
            studentId = "0000" + (id+1);

        return studentId;
    }

    public void deleteStudent(Long id){
        Student student = studentRepository.findByIdAndIsDeletedFalse(id);
        student.setIsDeleted(true);
        studentRepository.save(student);

    }
}
