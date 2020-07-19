package com.demo.dive.cube.controller;

import com.demo.dive.cube.config.URLConstants;
import com.demo.dive.cube.dto.StudentDto;
import com.demo.dive.cube.service.CommonService;
import com.demo.dive.cube.service.StudentService;
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
@RequestMapping("student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CommonService commonService;

    @GetMapping
    public ModelAndView getStudents(){
        ModelAndView modelAndView = new ModelAndView("student");
        modelAndView.addObject("studentDto",new StudentDto());
        modelAndView.addObject("countries",commonService.getCountryData());
        modelAndView.addObject("studentList",studentService.findAllStudents());
        return modelAndView;
    }

    @PostMapping(value = URLConstants.SAVE_URL)
    public String saveStudentInfo(@ModelAttribute StudentDto studentDto){
        ModelAndView modelAndView = new ModelAndView("student");
        studentService.saveNUpdateStudent(studentDto,modelAndView);
        return "redirect:/student";
    }

    @GetMapping(value = URLConstants.DELETE_URL)
    public String delete(@PathVariable Long id){
        studentService.deleteStudent(id);
        return "redirect:/student";
    }

    @GetMapping(value = URLConstants.EDIT_URL)
    public ModelAndView edit(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("student");
        modelAndView.addObject("countries",commonService.getCountryData());
        modelAndView.addObject("studentDto",studentService.findStudentById(id));
        modelAndView.addObject("studentList",studentService.findAllStudents());
        modelAndView.addObject("imageName","/studentImage/"+ studentService.getImageName(id));
        return modelAndView;
    }

    @GetMapping(value = URLConstants.REPORT)
    public ModelAndView getReportView(){
        ModelAndView modelAndView = new ModelAndView("studentReport");
        return modelAndView;
    }

    @PostMapping(value = URLConstants.EXPORT_REPORT)
    public void reportExport(HttpServletRequest request, HttpServletResponse response){
        studentService.studentReport(request,response);

    }

    @GetMapping(value = "/count"+ URLConstants.REPORT)
    public ModelAndView getCountReportView(){
        ModelAndView modelAndView = new ModelAndView("studentCountReport");
        return modelAndView;
    }

    @PostMapping(value = "/count"+ URLConstants.EXPORT_REPORT,  params="action=pdf")
    public void reportCountExport(HttpServletRequest request, HttpServletResponse response){
        studentService.studentCountReport(request,response);

    }

    @PostMapping(value = URLConstants.EXPORT_REPORT,  params="action=excel")
    public void excelExport( HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String filename = "student-"+ new Date().getTime()+".xlsx";
        response.setHeader("Content-Disposition", "attachment; filename="+filename);
        ByteArrayInputStream stream = studentService.excelReport(request,response);
        IOUtils.copy(stream, response.getOutputStream());

    }

}
