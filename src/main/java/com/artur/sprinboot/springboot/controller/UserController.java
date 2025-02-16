package com.artur.sprinboot.springboot.controller;

import com.artur.sprinboot.springboot.model.User;
import com.artur.sprinboot.springboot.service.UserServiceImpl;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class UserController {
    private final UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/user")
    public String showUser(Model model, Principal principal) {
        User user = userServiceImpl.findByUsername(principal.getName());
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/user/details/{id}")
    public String showUserDetails(@PathVariable("id") Long id, Model model) {
        User user = userServiceImpl.findUser(id); // Метод в сервисе для поиска пользователя по ID
        model.addAttribute("user", user);
        return "user"; // Название HTML-шаблона (user-details.html)
    }
}


