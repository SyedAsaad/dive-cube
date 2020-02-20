package com.demo.dive.cube.controller;

import com.demo.dive.cube.config.URLConstants;
import com.demo.dive.cube.dto.PaymentDto;
import com.demo.dive.cube.model.Item;
import com.demo.dive.cube.service.ItemService;
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

    @GetMapping("/supplier")
    public ModelAndView getPaymentSupplier(Model model){
        ModelAndView modelAndView = new ModelAndView("supplierPayment");
        modelAndView.addObject("payment",new PaymentDto());
        modelAndView.addObject("items",itemService.findAll());
//        modelAndView.addObject("items",itemService.findAll());
        return modelAndView;
    }
    @PostMapping(value = "/supplier"+ URLConstants.SAVE_URL)
    public String save(@ModelAttribute PaymentDto paymentDto){
//        itemService.save(item);
        System.out.println(paymentDto);
        return "redirect:/item";
    }
}
