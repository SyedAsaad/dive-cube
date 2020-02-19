package com.demo.dive.cube.controller;

import com.demo.dive.cube.config.URLConstants;
import com.demo.dive.cube.model.Supplier;
import com.demo.dive.cube.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public ModelAndView getSupplier(){
        ModelAndView modelAndView = new ModelAndView("supplier");
        modelAndView.addObject("supplier",new Supplier());
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
        modelAndView.addObject("supplier",supplierService.findOne(id));
        modelAndView.addObject("suppliers",supplierService.findAll());
        return modelAndView;
    }
}

