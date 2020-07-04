package com.demo.dive.cube.controller;

import com.demo.dive.cube.config.URLConstants;
import com.demo.dive.cube.model.Report;
import com.demo.dive.cube.model.Supplier;
import com.demo.dive.cube.service.CommonService;
import com.demo.dive.cube.service.SupplierService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private CommonService commonService;

    @GetMapping
    public ModelAndView getSupplier() throws JsonProcessingException {
        commonService.getCountryData();
        ModelAndView modelAndView = new ModelAndView("supplier");
        modelAndView.addObject("supplier",new Supplier());
        modelAndView.addObject("countries",commonService.getCountryData());
        modelAndView.addObject("suppliers",supplierService.findAll());
        return modelAndView;
    }

    @PostMapping(value = URLConstants.SAVE_URL)
    public String save(@ModelAttribute Supplier supplier){
        supplierService.save(supplier);
        return "redirect:/supplier";
    }

    @GetMapping(value = URLConstants.DELETE_URL)
    public String delete(@PathVariable Long id){
        supplierService.delete(id);
        return "redirect:/supplier";
    }

    @GetMapping(value = URLConstants.EDIT_URL)
    public ModelAndView edit(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("supplier");
        modelAndView.addObject("countries",commonService.getCountryData());
        modelAndView.addObject("supplier",supplierService.findOne(id));
        modelAndView.addObject("suppliers",supplierService.findAll());
        return modelAndView;
    }

    @GetMapping(value = URLConstants.REPORT)
    public ModelAndView getReportView(){
        ModelAndView modelAndView = new ModelAndView("supplierContactForm");
        modelAndView.addObject("supplier",new Supplier());
        return modelAndView;
    }

    @PostMapping(value = URLConstants.EXPORT_REPORT)
    public void reportExport(@ModelAttribute Supplier supplier, HttpServletRequest request, HttpServletResponse response){
        supplierService.exportReport(supplier,request,response);
    }

    @GetMapping(value = "/sales"+URLConstants.REPORT)
    public ModelAndView getSaleReportView(){
        ModelAndView modelAndView = new ModelAndView("sales");
        return modelAndView;
    }

    @PostMapping(value = "/sales"+URLConstants.EXPORT_REPORT)
    public void reportExport( HttpServletRequest request, HttpServletResponse response){
        supplierService.exportSalesReport(request,response);
    }
}

