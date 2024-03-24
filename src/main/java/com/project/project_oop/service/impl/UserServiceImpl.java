package com.project.project_oop.service.impl;

import com.project.project_oop.model.User;
import com.project.project_oop.repository.UserRepository;
import com.project.project_oop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
