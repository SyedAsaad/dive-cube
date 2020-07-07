package com.demo.dive.cube.controller;

import com.demo.dive.cube.config.URLConstants;
import com.demo.dive.cube.dto.BeachLogDto;
import com.demo.dive.cube.dto.BoatLogDto;
import com.demo.dive.cube.enums.DiveName;
import com.demo.dive.cube.service.BeachLogService;
import com.demo.dive.cube.service.BoatLogService;
import com.demo.dive.cube.service.EnumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/beach/log")
public class BeachLogController {

    @Autowired
    private BeachLogService beachLogService;

    @Autowired
    private EnumService enumService;

    @GetMapping
    private ModelAndView getBeachLog(){
        ModelAndView modelAndView = new ModelAndView("beachLog");
        modelAndView.addObject("beachLog",new BeachLogDto());
        beachLogService.addDependentDetais(modelAndView);

        return modelAndView;
    }

    @GetMapping(value = URLConstants.DELETE_URL)
    private String delete(@PathVariable Long id){
        beachLogService.delete(id);
        return "redirect:/beach/log";
    }
    @PostMapping(value = URLConstants.SAVE_URL)
    private String saveBeachLog(@ModelAttribute BeachLogDto beachLogDto){
        beachLogService.save(beachLogDto);
        return "redirect:/beach/log";
    }

    @GetMapping(value = URLConstants.EDIT_URL)
    private ModelAndView edit(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("beachLog");
        modelAndView.addObject("beachLog",beachLogService.getDto(beachLogService.findOne(id)));
        beachLogService.addDependentDetais(modelAndView);
        return modelAndView;
    }

    @GetMapping(value = URLConstants.REPORT)
    public ModelAndView getEmployeeReportView(){
        ModelAndView modelAndView = new ModelAndView("beachLogReport");
        modelAndView.addObject("dives",enumService.getEnumList(DiveName.values()));
        return modelAndView;
    }

    @PostMapping(value = URLConstants.EXPORT_REPORT)
    public void reportEmployeeExport(HttpServletRequest request, HttpServletResponse response){
        beachLogService.exportReport(request,response);
    }
}
