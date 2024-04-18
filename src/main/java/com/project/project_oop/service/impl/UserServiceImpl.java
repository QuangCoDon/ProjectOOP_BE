package com.project.project_oop.service.impl;

import com.project.project_oop.dto.UserDTO;
import com.project.project_oop.model.User;
import com.project.project_oop.repository.UserRepository;
import com.project.project_oop.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private  ModelMapper modelMapper;

    @Override
    public UserDTO findByUsername(String username) {
        return modelMapper.map(userRepository.findByUsername(username), UserDTO.class);
    }
}
