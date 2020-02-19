package com.demo.dive.cube.controller;

import com.demo.dive.cube.dto.AuthenticationRequestDto;
import com.demo.dive.cube.dto.UserDto;
import com.demo.dive.cube.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/reg")
    private ModelAndView indexCall(){
        ModelAndView m = new ModelAndView();
        m.addObject("user",new UserDto());
        m.setViewName("registration");
        return m;
    }

    @PostMapping(value = "/registration")
    public String save(
           @Valid @ModelAttribute UserDto user){

        userService.saveUser(user);
        return "redirect:/";
    }
    @RequestMapping(value={"/","/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        modelAndView.addObject("authenticationRequest",new AuthenticationRequestDto());
        return modelAndView;
    }
    @RequestMapping(value="/userLogin", method = RequestMethod.POST)
    public String login(@ModelAttribute AuthenticationRequestDto authenticationRequestDto,HttpServletRequest httpServletRequest){
        userService.authenticate(authenticationRequestDto);
        return "redirect:/item";
    }


    @RequestMapping(value="/default", method = RequestMethod.GET)
    public String defaultAccess(HttpServletRequest request){

        if(request.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/item";
        }
        else if(request.isUserInRole("ROLE_USER")) {

            return "redirect:/rentout";

        }

        return "";
    }

}
