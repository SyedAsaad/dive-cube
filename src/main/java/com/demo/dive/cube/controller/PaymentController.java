package com.demo.dive.cube.controller;

import com.demo.dive.cube.config.URLConstants;
import com.demo.dive.cube.dto.PaymentDto;
import com.demo.dive.cube.enums.PaymentMethod;
import com.demo.dive.cube.enums.PaymentType;
import com.demo.dive.cube.model.Payment;
import com.demo.dive.cube.service.EnumService;
import com.demo.dive.cube.service.OrderService;
import com.demo.dive.cube.service.PaymentService;
import com.demo.dive.cube.service.SupplierService;
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
    private PaymentService paymentService;

    @Autowired
    private EnumService enumService;

    @Autowired
    private SupplierService supplierService;

    @GetMapping(value = {"/{orderId}/"})
    public ModelAndView getPaymentSupplier(@PathVariable String orderId){
        ModelAndView modelAndView = new ModelAndView("payment");
        modelAndView.addObject("payment",new PaymentDto(orderId));
        modelAndView.addObject("paymentMethods",enumService.getEnumList(PaymentMethod.values()));
        modelAndView.addObject("paymentTypes",enumService.getEnumList(PaymentType.values()));
        modelAndView.addObject("suppliersPayment", paymentService.findAll(orderId));
        modelAndView.addObject("orders",orderService.findAll());
        return modelAndView;
    }
    @PostMapping(value = {"/{id}"+ URLConstants.SAVE_URL,URLConstants.EDIT_URL+URLConstants.SAVE_URL})
    public String save(@PathVariable String id , @ModelAttribute PaymentDto paymentDto){
        String orderId = paymentService.savePayment(paymentDto);
        return "redirect:/payment/"+orderId+"/";
    }

    @GetMapping(value = URLConstants.DELETE_URL)
    public String delete(@PathVariable Long id){
        String url =paymentService.delete(id);
        return "redirect:/"+url;
    }

    @GetMapping(value =  URLConstants.EDIT_URL)
    public ModelAndView edit(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("payment");
        PaymentDto payment=paymentService.findOne(id);
        modelAndView.addObject("payment",payment );
        modelAndView.addObject("paymentMethods",enumService.getEnumList(PaymentMethod.values()));
        modelAndView.addObject("paymentTypes",enumService.getEnumList(PaymentType.values()));
        modelAndView.addObject("suppliersPayment", paymentService.findAll(payment.getOrderId()));
        modelAndView.addObject("orders",orderService.findAll());
        return modelAndView;
    }
}
