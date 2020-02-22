package com.demo.dive.cube.controller;

import com.demo.dive.cube.config.URLConstants;
import com.demo.dive.cube.dto.PaymentDto;
import com.demo.dive.cube.model.Item;
import com.demo.dive.cube.service.ItemService;
import com.demo.dive.cube.service.SupplierPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private SupplierPaymentService supplierPaymentService;

    @GetMapping("/supplier")
    public ModelAndView getPaymentSupplier(){
        ModelAndView modelAndView = new ModelAndView("supplierPayment");
        modelAndView.addObject("payment",new PaymentDto());
//        modelAndView.addObject("pa",supplierPaymentService.findAll());
//        modelAndView.addObject("items",itemService.findAll());
        return modelAndView;
    }
    @PostMapping(value = "/supplier"+ URLConstants.SAVE_URL)
    public String save(@ModelAttribute PaymentDto paymentDto){
        supplierPaymentService.savePayment(paymentDto);
        return "redirect:/item";
    }
}
