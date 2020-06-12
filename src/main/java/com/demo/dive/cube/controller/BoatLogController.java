package com.demo.dive.cube.controller;

import com.demo.dive.cube.config.URLConstants;
import com.demo.dive.cube.dto.BoatLogDto;
import com.demo.dive.cube.service.BoatLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/boat/log")
public class BoatLogController {



    @Autowired
    private BoatLogService boatLogService;

    @GetMapping
    private ModelAndView getBoatLog(){
        ModelAndView modelAndView = new ModelAndView("boatLog");
        modelAndView.addObject("boatLog",new BoatLogDto());
        boatLogService.addDependentDetais(modelAndView);

        return modelAndView;
    }

    @GetMapping(value = URLConstants.DELETE_URL)
    private String delete(@PathVariable Long id){
        boatLogService.delete(id);
        return "redirect:/boat/log";
    }
    @PostMapping(value = URLConstants.SAVE_URL)
    private String saveBoatLog(@ModelAttribute BoatLogDto boatLogDto){
        boatLogService.save(boatLogDto);
        return "redirect:/boat/log";
    }

    @GetMapping(value = URLConstants.EDIT_URL)
    private ModelAndView edit(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("boatLog");
        modelAndView.addObject("boatLog",boatLogService.getDto(boatLogService.findOne(id)));
        boatLogService.addDependentDetais(modelAndView);
        return modelAndView;
    }
}
