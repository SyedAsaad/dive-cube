package com.demo.dive.cube.controller;

import com.demo.dive.cube.config.URLConstants;
import com.demo.dive.cube.dto.BoatLogDto;
import com.demo.dive.cube.enums.DiveName;
import com.demo.dive.cube.service.BoatLogService;
import com.demo.dive.cube.service.EnumService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;

@Controller
@RequestMapping("/boat/log")
public class BoatLogController {

    @Autowired
    private EnumService enumService;

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

    @GetMapping(value = URLConstants.REPORT)
    public ModelAndView getEmployeeReportView(){
        ModelAndView modelAndView = new ModelAndView("boatLogReport");
        modelAndView.addObject("dives",enumService.getEnumList(DiveName.values()));
        return modelAndView;
    }

    @PostMapping(value = URLConstants.EXPORT_REPORT,params="action=pdf")
    public void reportEmployeeExport(HttpServletRequest request, HttpServletResponse response){
        boatLogService.exportReport(request,response);
    }

    @PostMapping(value = URLConstants.EXPORT_REPORT,  params="action=excel")
    public void excelEmployeeExport( HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String filename = "boat-log-"+ new Date().getTime()+".xlsx";
        response.setHeader("Content-Disposition", "attachment; filename="+filename);
        ByteArrayInputStream stream =  boatLogService.excelReport(request,response);
        IOUtils.copy(stream, response.getOutputStream());

    }
}
