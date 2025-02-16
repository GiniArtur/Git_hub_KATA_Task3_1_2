package com.artur.sprinboot.springboot.controller;

import com.artur.sprinboot.springboot.model.User;
import com.artur.sprinboot.springboot.service.RoleService;
import com.artur.sprinboot.springboot.service.UserService;
import com.artur.sprinboot.springboot.service.UserServiceImpl;
import com.artur.sprinboot.springboot.util.UserValidator;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final UserValidator userValidator;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, UserValidator userValidator, BCryptPasswordEncoder bCryptPasswordEncoder, UserServiceImpl userServiceImpl) {
        this.userService = userService;
        this.roleService = roleService;
        this.userValidator = userValidator;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getAll());
        return "user-list";
    }

    @GetMapping("/add")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "add";
    }

    @PostMapping("/add")
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "add";
        }
        logger.info("Сохранение пользователя: {}", user.getUsername());
        userService.add(user);
        logger.info("Пользователь сохранён с ролями: {}", user.getRoles());
        return "redirect:/admin";
    }

    @GetMapping("/user-update")
    public String showFormForUpdate(@RequestParam("id") long id, Model model) {
        model.addAttribute("user", userService.findUser(id));
        model.addAttribute("roles", roleService.getAllRoles());
        userService.findUser(id).setPassword("");
        return "user-update";
    }

    @PostMapping("/user-update")
    public String updateUser(@RequestParam("id") long id, @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "user-update";
        }
        userService.update(user, user.getRoles());
        return "redirect:/admin";
    }

    @PostMapping("/user-delete")
    public String delete(@RequestParam("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}