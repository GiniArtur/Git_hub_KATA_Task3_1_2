package com.artur.sprinboot.springboot.service;

import com.artur.sprinboot.springboot.model.Role;
import com.artur.sprinboot.springboot.model.User;
import com.artur.sprinboot.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, @Lazy BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " not found");
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public boolean add(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null) {
            return false;
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    @Transactional
    public boolean update(User updatedUser, List<Role> roles) {
        User userFromDB = userRepository.findByUsername(updatedUser.getUsername());
        if (userFromDB != null) {
            userFromDB.setUsername(updatedUser.getUsername());
            //Проверка нужно ли шифровать пароль
            if (userFromDB.getPassword().length() < 60) {
                userFromDB.setPassword(bCryptPasswordEncoder.encode(updatedUser.getPassword()));
            } else {
                userFromDB.setPassword(bCryptPasswordEncoder.encode(updatedUser.getPassword()));
            }
            userFromDB.setEmail(updatedUser.getEmail());
            userFromDB.setRoles(roles);
            userRepository.save(userFromDB);
            System.out.println("Старый пароль: " + userFromDB.getPassword());
            System.out.println("Новый пароль: " + updatedUser.getPassword());
            return true;
        } else {
            return false;

        }
    }

    @Override
    public User findUser(long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("User with id = " + id + " not exist"));
    }

    @Override
    @Transactional
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}



