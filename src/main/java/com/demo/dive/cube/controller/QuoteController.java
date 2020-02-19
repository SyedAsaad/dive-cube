package com.demo.dive.cube.controller;

import com.demo.dive.cube.config.URLConstants;
import com.demo.dive.cube.model.Quote;
import com.demo.dive.cube.service.ItemService;
import com.demo.dive.cube.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("quote")
public class QuoteController {
    @Autowired
    private QuoteService quoteService;

    @Autowired
    private ItemService itemService;

    @GetMapping
    public ModelAndView getSupplier(){
        ModelAndView modelAndView = new ModelAndView("quote");
        modelAndView.addObject("quote",new Quote());
        modelAndView.addObject("quotes",quoteService.findAll());
        modelAndView.addObject("items",itemService.findAll());
        return modelAndView;
    }

    @PostMapping(value = URLConstants.SAVE_URL)
    public String save(@ModelAttribute Quote quote){
        quoteService.save(quote);
        return "redirect:/quote";
    }

    @GetMapping(value = URLConstants.DELETE_URL)
    public String delete(@PathVariable Long id){
        quoteService.delete(id);
        return "redirect:/quote";
    }

    @GetMapping(value = URLConstants.EDIT_URL)
    public ModelAndView edit(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("quote");
        modelAndView.addObject("quote",quoteService.findOne(id));
        modelAndView.addObject("quotes",quoteService.findAll());
        modelAndView.addObject("items",itemService.findAll());
        return modelAndView;
    }
}
