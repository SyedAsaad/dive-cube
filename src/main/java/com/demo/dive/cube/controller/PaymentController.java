package com.demo.dive.cube.controller;

import com.demo.dive.cube.config.URLConstants;
import com.demo.dive.cube.dto.PaymentDto;
import com.demo.dive.cube.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private SupplierPaymentService supplierPaymentService;

    @Autowired
    private EnumService enumService;

    @Autowired
    private SupplierService supplierService;

    @GetMapping("/supplier")
    public ModelAndView getPaymentSupplier(){
        ModelAndView modelAndView = new ModelAndView("supplierPayment");
        modelAndView.addObject("payment",new PaymentDto());
        modelAndView.addObject("paymentMethods",orderService.getAllPaymentMethods());
        modelAndView.addObject("paymentTypes",supplierPaymentService.getAllPaymentTypes());
        modelAndView.addObject("suppliers",supplierService.findAll());
        modelAndView.addObject("suppliersPayment",supplierPaymentService.findAll());
        modelAndView.addObject("orders",orderService.findAll());
        return modelAndView;
    }
    @PostMapping(value = "/supplier"+ URLConstants.SAVE_URL)
    public String save(@ModelAttribute PaymentDto paymentDto){
        supplierPaymentService.savePayment(paymentDto);
        return "redirect:/payment/supplier";
    }

    @GetMapping(value = "/supplier"+ URLConstants.DELETE_URL)
    public String delete(@PathVariable Long id){
        supplierPaymentService.delete(id);
        return "redirect:/supplier";
    }

    @GetMapping(value = "/supplier"+ URLConstants.EDIT_URL)
    public ModelAndView edit(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("supplierPayment");
        modelAndView.addObject("payment",supplierPaymentService.findOne(id));
        modelAndView.addObject("paymentMethods",orderService.getAllPaymentMethods());
        modelAndView.addObject("paymentTypes",supplierPaymentService.getAllPaymentTypes());
        modelAndView.addObject("suppliers",supplierService.findAll());
        modelAndView.addObject("suppliersPayment",supplierPaymentService.findAll());
        modelAndView.addObject("orders",orderService.findAll());
        return modelAndView;
    }
}
