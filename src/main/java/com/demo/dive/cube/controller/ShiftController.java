package com.demo.dive.cube.controller;

import com.demo.dive.cube.config.URLConstants;
import com.demo.dive.cube.dto.StudentDto;
import com.demo.dive.cube.model.Shift;
import com.demo.dive.cube.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("shift")
public class ShiftController {

    @Autowired
    private ShiftService shiftService;

    @GetMapping
    public ModelAndView getShift(){
        ModelAndView modelAndView = new ModelAndView("shift");
        modelAndView.addObject("shift",new Shift());
        modelAndView.addObject("shiftList",shiftService.findAll());
        return modelAndView;
    }


    @PostMapping(value = URLConstants.SAVE_URL)
    public String saveShift(@ModelAttribute Shift shift){
        shiftService.saveShift(shift);
        return "redirect:/shift";
    }

    @GetMapping(value = URLConstants.DELETE_URL)
    public String delete(@PathVariable Long id){
        shiftService.deleteShift(id);
        return "redirect:/shift";
    }

    @GetMapping(value = URLConstants.EDIT_URL)
    public ModelAndView edit(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("shift");
        modelAndView.addObject("shift",shiftService.findOne(id));
        modelAndView.addObject("shiftList",shiftService.findAll());
        return modelAndView;
    }
}
