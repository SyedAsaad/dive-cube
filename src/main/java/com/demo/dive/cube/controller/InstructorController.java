package com.demo.dive.cube.controller;

import com.demo.dive.cube.config.URLConstants;
import com.demo.dive.cube.model.Instructor;
import com.demo.dive.cube.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("instructor")
public class InstructorController {
    @Autowired
    private InstructorService instructorService;

    @GetMapping
    public ModelAndView getInstructorService(){
        ModelAndView modelAndView = new ModelAndView("instructor");
        modelAndView.addObject("instructor",new Instructor());
        modelAndView.addObject("instructors",instructorService.findAll());
        return modelAndView;
    }

    @PostMapping(value = URLConstants.SAVE_URL)
    public String save(@ModelAttribute Instructor instructor){
        instructorService.save(instructor);
        return "redirect:/instructor";
    }

    @GetMapping(value = URLConstants.DELETE_URL)
    public String delete(@PathVariable Long id){
        instructorService.delete(id);
        return "redirect:/instructor";
    }

    @GetMapping(value = URLConstants.EDIT_URL)
    public ModelAndView edit(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("instructor");
        modelAndView.addObject("instructor",instructorService.findOne(id));
        modelAndView.addObject("instructors",instructorService.findAll());
        return modelAndView;
    }
}
