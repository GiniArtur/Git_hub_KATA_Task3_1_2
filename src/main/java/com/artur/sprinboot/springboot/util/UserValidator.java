package com.artur.sprinboot.springboot.util;

import com.artur.sprinboot.springboot.model.User;
import com.artur.sprinboot.springboot.service.UserService;
import com.artur.sprinboot.springboot.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public UserValidator(UserService userService, UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        // Проверяем, существует ли пользователь в базе
        if (userServiceImpl.existsByUsername(user.getUsername())) {
            errors.rejectValue("username", "", "User already exists");
        }
    }
}
