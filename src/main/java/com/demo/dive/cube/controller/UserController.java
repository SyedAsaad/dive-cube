package com.demo.dive.cube.controller;

import com.demo.dive.cube.config.URLConstants;
import com.demo.dive.cube.config.UtilService;
import com.demo.dive.cube.dto.AuthenticationRequestDto;
import com.demo.dive.cube.dto.UserDto;
import com.demo.dive.cube.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/registration")
    private ModelAndView registrationView(){
        ModelAndView m = new ModelAndView("registration");
        m.addObject("user",new UserDto());
        m.addObject("userList",userService.findAll());
        return m;
    }

    @PostMapping(value = "/saveUser")
    public String save(@ModelAttribute UserDto user){

        userService.saveNupdateUser(user);
        return "redirect:/registration";
    }
    @GetMapping(value = "/user"+ URLConstants.EDIT_URL)
    private ModelAndView editUser(@PathVariable Long id){
        ModelAndView modelAndView =  new ModelAndView("registration");
        modelAndView.addObject("user",userService.getUserDto(userService.findOne(id)));
        modelAndView.addObject("userList",userService.findAll());
        return modelAndView;
    }

    @GetMapping(value = "/user"+ URLConstants.DELETE_URL)
    private String deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return "redirect:/registration";
    }

    @RequestMapping(value={"/","/login"}, method = RequestMethod.GET)
    public ModelAndView login(){

        return userService.checkUserAuthenticate(new ModelAndView());
    }


    @RequestMapping(value="/userLogin", method = RequestMethod.POST)
    public String login(@ModelAttribute AuthenticationRequestDto authenticationRequestDto, HttpServletRequest httpServletRequest, Model model){

           Boolean isAuthenticated = userService.authenticate(authenticationRequestDto);
           if(isAuthenticated)
            return "redirect:/item";
           else{
               model.addAttribute("loginError", true);
               return "login.html"; }

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
