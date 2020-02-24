package com.demo.dive.cube.controller;

import com.demo.dive.cube.config.URLConstants;
import com.demo.dive.cube.model.DiveType;
import com.demo.dive.cube.service.DiveTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/dive")
public class DiveController {

    @Autowired
    private DiveTypeService diveService;

    @GetMapping
    public ModelAndView getDive(){
        ModelAndView modelAndView = new ModelAndView("dive");
        modelAndView.addObject("dive",new DiveType());
        modelAndView.addObject("dives",diveService.findAll());
        return modelAndView;
    }

    @PostMapping(value = URLConstants.SAVE_URL)
    public String save(@Valid  @ModelAttribute DiveType diveType){
        diveService.save(diveType);
        return "redirect:/dive";
    }

    @GetMapping(value = URLConstants.DELETE_URL)
    public String delete(@PathVariable Long id){
        diveService.delete(id);
        return "redirect:/dive";
    }

    @GetMapping(value = URLConstants.EDIT_URL)
    public ModelAndView edit(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("dive");
        modelAndView.addObject("dive",diveService.findOne(id));
        modelAndView.addObject("dives",diveService.findAll());
        return modelAndView;
    }
}
