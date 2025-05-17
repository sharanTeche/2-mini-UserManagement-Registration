package com.sun.controller;


import com.sun.dto.LoginDto;
import com.sun.dto.QuoteApiResponceDto;
import com.sun.dto.ResetPwdForm;
import com.sun.dto.UserDto;
import com.sun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

import static com.sun.constant.AppConstant.*;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String index(Model model) {
        LoginDto dto = new LoginDto();
        model.addAttribute("user", dto);
        return "index";
    }

    @PostMapping("/login")
    public String loginPage(@ModelAttribute("user")LoginDto user,Model model) {
        UserDto login = userService.login(user.getEmail(), user.getPwd());

        if (login == null) {
            model.addAttribute(ERROR_MSG,"Invalid credentails");
            return "index";
        }
       String password = login.getPwdUpdated();
        if (password.equals("NO")) {
            ResetPwdForm resetPwdDto = new ResetPwdForm();
            resetPwdDto.setUserId(login.getUserId());
            model.addAttribute("resetPwd",resetPwdDto);
            return RESET_PWD;
        }else {
            QuoteApiResponceDto quoteApiResponceDto = userService.getQuote();
            model.addAttribute("quote", quoteApiResponceDto);
            return "dashboard";

        }
    }

    @PostMapping("/updatePwd")
    public String updatePwd(@ModelAttribute("resetPwd") ResetPwdForm resetPwd, Model model) {

        if(!resetPwd.getNewPwd().equals(resetPwd.getConfirmPwd())) {
            model.addAttribute(ERROR_MSG, "Both Pwds should be same");
            return RESET_PWD;
        }

        boolean status = userService.resetPwd(resetPwd);

        if(status) {
            return "redirect:dashboard";
        }

        model.addAttribute(ERROR_MSG, "Pwd update failed");
        return RESET_PWD;

    }

    @GetMapping("/register")
    public String register(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto",userDto);
        model.addAttribute("countries", userService.getAllCountry());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserDto userDto, Model model) {

        boolean saveUser = userService.register(userDto);

        if(saveUser) {
            model.addAttribute(SUCESS_MSG, "Registration Success");
        }else {
            model.addAttribute(ERROR_MSG, "Registration Failed");
        }

        Map<Integer,String> countries = userService.getAllCountry();
        model.addAttribute("countries", countries);

        return "register";
    }

    @GetMapping("/dashboard")
    public String buildDashboard(Model model) {

        QuoteApiResponceDto quote = userService.getQuote();
        model.addAttribute("quote", quote);

        return "dashboard";
    }

    @GetMapping("/getStates")
    @ResponseBody
    public Map<Integer, String> getStateByState(@RequestParam("countryId") Integer countryId) {
        return userService.getStateByState(countryId);
    }

    //fetch all cities Based on stateId

    @GetMapping("/getCities")
    @ResponseBody
    public Map<Integer, String> getCitiesByState(@RequestParam("stateId") Integer stateId) {
        return userService.getCitiesByState(stateId);
    }


}
