package com.project.project_oop.service;

import com.project.project_oop.model.User;

public interface UserService {
    User findByUsername(String username);
}
