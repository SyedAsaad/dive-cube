package com.demo.dive.cube.controller;

import com.demo.dive.cube.config.JsonConverter;
import com.demo.dive.cube.config.URLConstants;
import com.demo.dive.cube.dto.StudentDto;
import com.demo.dive.cube.model.Student;
import com.demo.dive.cube.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/student")
public class StudentController {

    private StudentService studentService;

    final JsonConverter jsonConverter = new JsonConverter<>();

    public StudentController(final StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ModelAndView getStudents(){
        ModelAndView modelAndView = new ModelAndView("student");
//        modelAndView.addObject("student",new Student());
//        modelAndView.addObject("fileError",false);
        modelAndView.addObject("studentList",studentService.findAllStudents());
        return modelAndView;
    }

    @RequestMapping(name = URLConstants.SAVE_URL,method = {RequestMethod.POST, RequestMethod.PUT})
    public ModelAndView saveStudentInfo( @RequestParam(value = "file") MultipartFile image,
                                         @RequestParam(value = "name",required = false) String studentData){
        ModelAndView modelAndView = new ModelAndView("student");
        Student studentDto = (Student) jsonConverter.mapData(studentData,Student.class);
//        modelAndView.addObject("student",new Student());
        modelAndView = studentService.saveNUpdateStudent(studentDto,image,modelAndView);
        return modelAndView;
    }

    @GetMapping(value = URLConstants.DELETE_URL)
    public String delete(@PathVariable Long id){
        studentService.deleteStudent(id);
        return "redirect:/student";
    }

    @GetMapping(value = URLConstants.EDIT_URL)
    public ModelAndView edit(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("student");
//        modelAndView.addObject("student",studentService.findStudentById(id));
        modelAndView.addObject("studentList",studentService.findAllStudents());
        return modelAndView;
    }
}
