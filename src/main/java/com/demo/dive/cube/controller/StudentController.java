package com.demo.dive.cube.controller;

import com.demo.dive.cube.config.JsonConverter;
import com.demo.dive.cube.config.URLConstants;
import com.demo.dive.cube.dto.StudentDto;
import com.demo.dive.cube.model.Student;
import com.demo.dive.cube.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public ModelAndView getStudents(){
        ModelAndView modelAndView = new ModelAndView("student");
        modelAndView.addObject("studentDto",new StudentDto());
        modelAndView.addObject("studentList",studentService.findAllStudents());
        return modelAndView;
    }

    @PostMapping(value = URLConstants.SAVE_URL)
    public String saveStudentInfo(@ModelAttribute StudentDto studentDto){
        ModelAndView modelAndView = new ModelAndView("student");
        studentService.saveNUpdateStudent(studentDto,modelAndView);
        return "redirect:/student";
    }

    @GetMapping(value = URLConstants.DELETE_URL)
    public String delete(@PathVariable Long id){
        studentService.deleteStudent(id);
        return "redirect:/student";
    }

    @GetMapping(value = URLConstants.EDIT_URL)
    public ModelAndView edit(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("student");
        modelAndView.addObject("studentDto",studentService.findStudentById(id));
        modelAndView.addObject("studentList",studentService.findAllStudents());
        modelAndView.addObject("imageName","/studentImage/"+ studentService.getImageName(id));
        return modelAndView;
    }
}
