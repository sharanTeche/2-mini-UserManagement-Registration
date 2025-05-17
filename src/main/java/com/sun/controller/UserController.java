package com.sun.controller;


import com.sun.dto.LoginDto;
import com.sun.dto.QuoteApiResponceDto;
import com.sun.dto.ResetPwdForm;
import com.sun.dto.UserDto;
import com.sun.entity.User;
import com.sun.service.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
            model.addAttribute("emsg","Invalid credentails");
            return "index";
        }
       String password = login.getPwdUpdated();
        if (password.equals("NO")) {
            ResetPwdForm resetPwdDto = new ResetPwdForm();
            resetPwdDto.setUserId(login.getUserId());
            model.addAttribute("resetPwd",resetPwdDto);
            return "resetPwd";
        }else {
            QuoteApiResponceDto quoteApiResponceDto = userService.getQuote();
            model.addAttribute("quote", quoteApiResponceDto);
            return "dashboard";

        }
    }

    @PostMapping("/updatePwd")
    public String updatePwd(@ModelAttribute("resetPwd") ResetPwdForm resetPwd, Model model) {

        if(!resetPwd.getNewPwd().equals(resetPwd.getConfirmPwd())) {
            model.addAttribute("errMsg", "Both Pwds should be same");
            return "resetPwd";
        }

        boolean status = userService.resetPwd(resetPwd);

        if(status) {
            return "redirect:dashboard";
        }

        model.addAttribute("errMsg", "Pwd update failed");
        return "resetPwd";

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
            model.addAttribute("succMsg", "Registration Success");
        }else {
            model.addAttribute("errMsg", "Registration Failed");
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

  /*  @GetMapping("/allCountry")
    public Map<Integer, String> getAllCountry() {
        return userService.getAllCountry();
    }

    //fetch allStates based on countryId

    @GetMapping("/getStates/{countryId}")
    public Map<Integer, String> getStateByState(@PathVariable("countryId") Integer countryId) {
        return userService.getStateByState(countryId);
    }

    @GetMapping("/getCities")
    public Map<Integer, String> getCitiesByState(@RequestParam("stateId") Integer stateId) {
        return userService.getCitiesByState(stateId);
    }

    @PostMapping("/register")
    public boolean register(@RequestBody UserDto userDto) {
        return userService.register(userDto);
    }
*/
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
