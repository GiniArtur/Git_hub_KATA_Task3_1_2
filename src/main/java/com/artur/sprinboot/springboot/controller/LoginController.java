package com.artur.sprinboot.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String welcomeToPage() {    //welcome to... - это повелительная форма глагола "to welcome"
        return "login";
    }
}

