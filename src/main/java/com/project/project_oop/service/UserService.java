package com.project.project_oop.service;

import com.project.project_oop.dto.UserDTO;
import com.project.project_oop.model.User;

import java.util.Optional;

public interface UserService {
    UserDTO findByUsername(String username);

//    boolean updateUser(User)

}
