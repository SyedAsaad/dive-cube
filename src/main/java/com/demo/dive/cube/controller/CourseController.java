package com.demo.dive.cube.controller;

import com.demo.dive.cube.config.URLConstants;
import com.demo.dive.cube.model.Course;
import com.demo.dive.cube.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping
    public ModelAndView getCourse(){
        ModelAndView modelAndView = new ModelAndView("course");
        modelAndView.addObject("course",new Course());
        modelAndView.addObject("courses",courseService.findAll());
        return modelAndView;
    }

    @PostMapping(value = URLConstants.SAVE_URL)
    public String save(@ModelAttribute Course course){
        courseService.save(course);
        return "redirect:/course";
    }

    @GetMapping(value = URLConstants.DELETE_URL)
    public String delete(@PathVariable Long id){
        courseService.delete(id);
        return "redirect:/course";
    }

    @GetMapping(value = URLConstants.EDIT_URL)
    public ModelAndView edit(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("course");
        modelAndView.addObject("course",courseService.findOne(id));
        modelAndView.addObject("courses",courseService.findAll());
        return modelAndView;
    }
}
