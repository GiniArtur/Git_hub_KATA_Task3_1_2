package com.artur.sprinboot.springboot.init;

import com.artur.sprinboot.springboot.model.Role;
import com.artur.sprinboot.springboot.model.User;
import com.artur.sprinboot.springboot.repository.RoleRepository;
import com.artur.sprinboot.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UsersInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserService userService;

    @Autowired
    public UsersInitializer(RoleRepository roleRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findByName("ROLE_USER").isEmpty()) {
            roleRepository.save(new Role("ROLE_USER"));
        }
        if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
            roleRepository.save(new Role("ROLE_ADMIN"));
        }
        userService.add(new User("user1", "user1", "user1@mail.ru", roleRepository.findAll()));
        userService.add(new User("user2", "user2", "user2@mail.ru", roleRepository.findAll()));
    }
}