package com.demo.dive.cube.controller;

import com.demo.dive.cube.config.URLConstants;
import com.demo.dive.cube.model.OrderDetail;
import com.demo.dive.cube.service.ItemService;
import com.demo.dive.cube.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("order/detail")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private ItemService itemService;

    @GetMapping
    public ModelAndView getOrderDetail(){
        ModelAndView modelAndView = new ModelAndView("orderDetail");
        modelAndView.addObject("orderDetail",new OrderDetail());
        modelAndView.addObject("orderDetail",orderDetailService.findAll());
        modelAndView.addObject("items",itemService.findAll());
        return modelAndView;
    }

    @PostMapping(value = URLConstants.SAVE_URL)
    public String save(@ModelAttribute OrderDetail orderDetail){
        orderDetailService.save(orderDetail);
        return "redirect:/order/detail";
    }

    @GetMapping(value = URLConstants.DELETE_URL)
    public String delete(@PathVariable Long id){
        orderDetailService.delete(id);
        return "redirect:/order/detail";
    }

    @GetMapping(value = URLConstants.EDIT_URL)
    public ModelAndView edit(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("orderDetail");
        modelAndView.addObject("orderDetail",new OrderDetail());
        modelAndView.addObject("orderDetail",orderDetailService.findAll());
        modelAndView.addObject("items",itemService.findAll());
        return modelAndView;
    }
}
