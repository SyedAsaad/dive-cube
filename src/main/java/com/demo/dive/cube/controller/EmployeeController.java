package com.demo.dive.cube.controller;

import com.demo.dive.cube.config.URLConstants;
import com.demo.dive.cube.dto.EmployeeDto;
import com.demo.dive.cube.model.Employee;
import com.demo.dive.cube.service.CommonService;
import com.demo.dive.cube.service.EmployeeService;
import com.demo.dive.cube.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ShiftService shiftService;

    @GetMapping
    public ModelAndView getEmployee(){
        ModelAndView modelAndView = new ModelAndView("employee");
        modelAndView.addObject("employee",new EmployeeDto());
        modelAndView.addObject("countries",commonService.getCountryData());
        modelAndView.addObject("shiftList",shiftService.findAll());
        modelAndView.addObject("employees",employeeService.findAll());
        modelAndView.addObject("employmentTypes",employeeService.getAllEmploymentType());
        return modelAndView;
    }

    @PostMapping(value = URLConstants.SAVE_URL)
    public String save(@ModelAttribute EmployeeDto employeeDto){
        employeeService.save(employeeDto);
        return "redirect:/employee";
    }

    @GetMapping(value = URLConstants.DELETE_URL)
    public String delete(@PathVariable Long id){
        employeeService.delete(id);
        return "redirect:/employee";
    }

    @GetMapping(value = URLConstants.EDIT_URL)
    public ModelAndView edit(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("employee");
        modelAndView.addObject("employee",employeeService.getEmployeeDto(employeeService.findOne(id)));
        modelAndView.addObject("countries",commonService.getCountryData());
        modelAndView.addObject("employees",employeeService.findAll());
        modelAndView.addObject("shiftList",shiftService.findAll());
        modelAndView.addObject("employmentTypes",employeeService.getAllEmploymentType());
        return modelAndView;
    }
}
