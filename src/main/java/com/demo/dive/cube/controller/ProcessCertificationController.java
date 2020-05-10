package com.demo.dive.cube.controller;

import com.demo.dive.cube.config.URLConstants;
import com.demo.dive.cube.dto.ProcessCertificationDto;
import com.demo.dive.cube.enums.CertificationType;
import com.demo.dive.cube.enums.PaymentMethod;
import com.demo.dive.cube.repository.ProcessCertificateRepository;
import com.demo.dive.cube.service.BookCourseService;
import com.demo.dive.cube.service.EnumService;
import com.demo.dive.cube.service.ProcessCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("process/certificate")
public class ProcessCertificationController {

    @Autowired
    private ProcessCertificateService processCertificateService;

    @Autowired
    private EnumService enumService;

    @GetMapping("/{bookingId}/")
    public ModelAndView getProcessCertificate(@PathVariable String bookingId){
        ModelAndView modelAndView = new ModelAndView("processCertificate");
        modelAndView.addObject("certificate",processCertificateService.getNewObject(bookingId));
        modelAndView.addObject("certificateTypes",enumService.getEnumList(CertificationType.values()));
        modelAndView.addObject("processCertificateList",processCertificateService.findAll());
        return modelAndView;
    }

    @PostMapping(value = {"/{id}"+ URLConstants.SAVE_URL,URLConstants.EDIT_URL+URLConstants.SAVE_URL})
    public String saveProcessCertificate(@PathVariable String id, @ModelAttribute ProcessCertificationDto processCertificationDto){
        processCertificateService.saveCertificateProcess(processCertificationDto);
        return "redirect:/process/certificate/"+processCertificationDto.getBookingId()+"/";
    }

    @GetMapping(value = URLConstants.DELETE_URL)
    public String delete(@PathVariable Long id){
        processCertificateService.deleteCertificateProcess(id);
        return "redirect:/book/course";
    }

    @GetMapping(value = URLConstants.EDIT_URL)
    public ModelAndView edit(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("processCertificate");
        modelAndView.addObject("certificate",processCertificateService.getNewObject(id));
        modelAndView.addObject("certificateTypes",enumService.getEnumList(CertificationType.values()));
        modelAndView.addObject("processCertificateList",processCertificateService.findAll());
        return modelAndView;
    }
}
