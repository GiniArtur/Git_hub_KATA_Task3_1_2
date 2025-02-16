package com.artur.sprinboot.springboot.service;

import com.artur.sprinboot.springboot.model.Role;
import com.artur.sprinboot.springboot.model.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    boolean add(User user);

    boolean update(User updatedUser, List<Role> roles);

    User findUser(long id);

    void delete(long id);

    User findByUsername(String username);

    boolean existsByUsername(String username);
}
