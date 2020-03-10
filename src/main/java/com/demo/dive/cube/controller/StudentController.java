package com.demo.dive.cube.controller;

import com.demo.dive.cube.dto.StudentDto;
import com.demo.dive.cube.model.Student;
import com.demo.dive.cube.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/student")
public class StudentController {

    private StudentService studentService;

    public StudentController(final StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ModelAndView getStudents(){
        ModelAndView modelAndView = new ModelAndView("student");
        modelAndView.addObject("student",new StudentDto());
        return modelAndView;
    }

    @RequestMapping(name = "/save",method = { RequestMethod.POST, RequestMethod.PUT },
            consumes = { "multipart/form-data" })
    public ModelAndView saveStudentInfo( @RequestParam("file") MultipartFile image,
                                         @RequestParam("studentDto") String studentData){
        ModelAndView modelAndView = new ModelAndView("student");
        modelAndView.addObject("student",new StudentDto());
        return modelAndView;
    }
}
