package com.demo.dive.cube.controller;

import com.demo.dive.cube.config.URLConstants;
import com.demo.dive.cube.model.BookDive;
import com.demo.dive.cube.service.BookDiveService;
import com.demo.dive.cube.service.DiveTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/book/dive")
public class BookDiveController {

    @Autowired
    private DiveTypeService diveService;

    @Autowired
    private BookDiveService bookDiveService;

    @GetMapping
    private ModelAndView getBookDive(){
     ModelAndView modelAndView = new ModelAndView("bookDive");
     modelAndView.addObject("dive",new BookDive());
     modelAndView.addObject("diveTypes",diveService.findAll());
     modelAndView.addObject("bookList",bookDiveService.findAll());
     return modelAndView;
    }

    @PostMapping(value = URLConstants.SAVE_URL)
    public String save(@Valid @ModelAttribute BookDive bookDive){
        bookDiveService.save(bookDive);
        return "redirect:/book/dive";
    }

    @GetMapping(value = URLConstants.DELETE_URL)
    public String delete(@PathVariable Long id){
        bookDiveService.delete(id);
        return "redirect:/book/dive";
    }

    @GetMapping(value = URLConstants.EDIT_URL)
    public ModelAndView edit(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("bookDive");
        modelAndView.addObject("dive",bookDiveService.findOne(id));
        modelAndView.addObject("diveTypes",diveService.findAll());
        modelAndView.addObject("bookList",bookDiveService.findAll());
        return modelAndView;
    }
}
