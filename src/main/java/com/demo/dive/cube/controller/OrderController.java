package com.demo.dive.cube.controller;

import com.demo.dive.cube.config.URLConstants;
import com.demo.dive.cube.enums.PaymentMethod;
import com.demo.dive.cube.model.Order;
import com.demo.dive.cube.service.EnumService;
import com.demo.dive.cube.service.OrderService;
import com.demo.dive.cube.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private EnumService enumService;

    @GetMapping
    public ModelAndView getOrder(){
        ModelAndView modelAndView = new ModelAndView("order");
        modelAndView.addObject("order",new Order());
        modelAndView.addObject("orders",orderService.findAll());
        modelAndView.addObject("suppliers",supplierService.findAll());
        modelAndView.addObject("paymentMethods",enumService.getEnumList(PaymentMethod.values()));
        return modelAndView;
    }

    @PostMapping(value = URLConstants.SAVE_URL)
    public String save(@ModelAttribute Order order){
        orderService.save(order);
        return "redirect:/order";
    }

    @GetMapping(value = URLConstants.DELETE_URL)
    public String delete(@PathVariable Long id){
        orderService.delete(id);
        return "redirect:/order";
    }

    @GetMapping(value = URLConstants.EDIT_URL)
    public ModelAndView edit(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("order");
        modelAndView.addObject("order",orderService.findOne(id));
        modelAndView.addObject("orders",orderService.findAll());
        modelAndView.addObject("suppliers",supplierService.findAll());
        modelAndView.addObject("paymentMethods",enumService.getEnumList(PaymentMethod.values()));
        return modelAndView;
    }

    @GetMapping(value = URLConstants.REPORT)
    public ModelAndView getReportView(){
        ModelAndView modelAndView = new ModelAndView("detailOrderForm");
        return modelAndView;
    }

    @PostMapping(value = URLConstants.EXPORT_REPORT)
    public void reportExport(HttpServletRequest request, HttpServletResponse response){

        orderService.exportReport(request,response);
    }
}
