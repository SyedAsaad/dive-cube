package com.demo.dive.cube.controller;

import com.demo.dive.cube.config.URLConstants;
import com.demo.dive.cube.model.Item;
import com.demo.dive.cube.service.CommonService;
import com.demo.dive.cube.service.ItemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @Autowired
    private CommonService commonService;

    @GetMapping
    public ModelAndView getItem() throws JsonProcessingException {
        commonService.getCountryData();
        ModelAndView modelAndView = new ModelAndView("item");
        modelAndView.addObject("item",new Item(itemService.generateItemId()));
        modelAndView.addObject("items",itemService.findAll());
        return modelAndView;
    }

    @PostMapping(value = URLConstants.SAVE_URL)
    public String save(@ModelAttribute Item item){
        itemService.save(item);
        return "redirect:/item";
    }

    @GetMapping(value = URLConstants.DELETE_URL)
    public String delete(@PathVariable Long id){
        itemService.delete(id);
        return "redirect:/item";
    }

    @GetMapping(value = URLConstants.EDIT_URL)
    public ModelAndView edit(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("item");
        modelAndView.addObject("item",itemService.findOne(id));
        modelAndView.addObject("items",itemService.findAll());
        return modelAndView;
    }
}
