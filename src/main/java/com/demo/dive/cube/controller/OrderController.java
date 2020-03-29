package com.demo.dive.cube.controller;

import com.demo.dive.cube.model.Order;
import com.demo.dive.cube.service.EnumService;
import com.demo.dive.cube.service.ItemService;
import com.demo.dive.cube.service.OrderService;
import com.demo.dive.cube.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class OrderController {

    private static final String AJAX_HEADER_NAME = "X-Requested-With";
    private static final String AJAX_HEADER_VALUE = "XMLHttpRequest";

    @Autowired
    private OrderService orderService;

    @Autowired
    private EnumService enumService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private ItemService itemService;

    @GetMapping("/order")
    private ModelAndView viewOrder(){
        ModelAndView modelAndView = new ModelAndView("supplierOrder");
        modelAndView.addObject("order",new Order());
        modelAndView.addObject("suppliers",supplierService.findAll());
        modelAndView.addObject("paymentMethods",enumService.findAllPaymentMethod());
        modelAndView.addObject("items",itemService.findAll());
        return  modelAndView;
    }

    @PostMapping(path = {"/order/addItem"})
    public String addOrder(Order order, HttpServletRequest request) {
        order.addOrderDetail();
        if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            // It is an Ajax request, render only #items fragment of the page.
            return "supplierOrder::#orderDetail";
        } else {
            // It is a standard HTTP request, render whole page.
            return "supplierOrder";
        }
    }

    @PostMapping("/order/supplier/save")
    public String addOrderList(@ModelAttribute Order order, HttpServletRequest request) {
        return null;
    }
}
