package com.demo.dive.cube.controller;

import com.demo.dive.cube.config.URLConstants;
import com.demo.dive.cube.dto.BookCourseDto;
import com.demo.dive.cube.model.BookCourse;
import com.demo.dive.cube.service.BookCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/book/course")
public class BookCourseController {

    @Autowired
    private BookCourseService bookCourseService;
    @Value("${book.course.file.path}")
    private String fileLocation;


    @GetMapping
    private ModelAndView getBookCourse(){
        ModelAndView modelAndView = new ModelAndView("bookCourse");
        modelAndView.addObject("bookCourse",new BookCourseDto());
        return bookCourseService.addDependentDetails(modelAndView);
    }

    @PostMapping(value = URLConstants.SAVE_URL)
    private String saveBookCourse(@ModelAttribute BookCourseDto bookCourseDto){
//        System.out.println(bookCourse);
        bookCourseService.saveCourseBooking(bookCourseDto,bookCourseDto.getClaimFile());
        return "redirect:/book/course";
    }

    @GetMapping(value = URLConstants.DELETE_URL)
    private String deleteBookCourse(@PathVariable Long id){
        bookCourseService.deleteBooking(id);
        return "redirect:/book/course";
    }

    @GetMapping(value = "/completed/{id}")
    private String updateCourseStatus(@PathVariable Long id){
        bookCourseService.updateCourseStatus(id);
        return "redirect:/book/course";
    }

    @GetMapping(value = URLConstants.EDIT_URL)
    private ModelAndView editBookCourse(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("bookCourse");
        modelAndView.addObject("bookCourse",bookCourseService.getBookCourseDto(id));
        modelAndView.addObject("fileName","/"+fileLocation+ bookCourseService.getFileNameById(id));
        return bookCourseService.addDependentDetails(modelAndView);
    }
}
