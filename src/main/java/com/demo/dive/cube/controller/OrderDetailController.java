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

    @GetMapping(value = {"/{orderId}/"})
    public ModelAndView getOrderDetail(@PathVariable Long orderId){
        ModelAndView modelAndView = new ModelAndView("orderDetail");
        modelAndView.addObject("orderDetail",new OrderDetail());
        modelAndView.addObject("orderDetails",orderDetailService.findAllByOrderId(orderId));
        modelAndView.addObject("items",itemService.findAll());
        return modelAndView;
    }

    @PostMapping(value = {"/{id}"+URLConstants.SAVE_URL,URLConstants.EDIT_URL+URLConstants.SAVE_URL})
    public String save(@ModelAttribute OrderDetail orderDetail,@PathVariable Long id){
        Long orderId = orderDetailService.save(orderDetail,id);
        return "redirect:/order/detail/"+orderId+"/";
    }

    @GetMapping(value = URLConstants.DELETE_URL)
    public String delete(@PathVariable Long id){
        Long orderId = orderDetailService.delete(id);
        return "redirect:/order/detail/"+orderId+"/";
    }

    @GetMapping(value = URLConstants.EDIT_URL)
    public ModelAndView edit(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("orderDetail");
        modelAndView.addObject("orderDetail",orderDetailService.findOne(id));
        modelAndView.addObject("orderDetails",orderDetailService.findAllByOrderId(orderDetailService.getOrderByDetailId(id).getId()));
        modelAndView.addObject("items",itemService.findAll());
        return modelAndView;
    }
}
