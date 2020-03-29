package com.demo.dive.cube.controller;

import com.demo.dive.cube.config.URLConstants;
import com.demo.dive.cube.enums.ClassSection;
import com.demo.dive.cube.model.ClassRoom;
import com.demo.dive.cube.service.ClassRoomService;
import com.demo.dive.cube.service.EnumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("class/room")
public class ClassRoomController {

    @Autowired
    private ClassRoomService classRoomService;

    @Autowired
    private EnumService enumService;

    @GetMapping
    public ModelAndView getClassRoom(){
        ModelAndView modelAndView = new ModelAndView("classRoom");
        modelAndView.addObject("classRoom",new ClassRoom());
        modelAndView.addObject("classRooms",classRoomService.findAll());
        modelAndView.addObject("sections",enumService.getEnumList(ClassSection.values()));
        return modelAndView;
    }

    @PostMapping(value = URLConstants.SAVE_URL)
    public String save(@ModelAttribute ClassRoom classRoom){
        classRoomService.save(classRoom);
        return "redirect:/class/room";
    }

    @GetMapping(value = URLConstants.DELETE_URL)
    public String delete(@PathVariable Long id){
        classRoomService.delete(id);
        return "redirect:/class/room";
    }

    @GetMapping(value = URLConstants.EDIT_URL)
    public ModelAndView edit(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("classRoom");
        modelAndView.addObject("classRoom",classRoomService.findOne(id));
        modelAndView.addObject("classRooms",classRoomService.findAll());
        modelAndView.addObject("sections",enumService.getEnumList(ClassSection.values()));
        return modelAndView;
    }
}
