package com.project.project_oop.config.security.user;

import com.project.project_oop.model.User;
import com.project.project_oop.repository.UserRepository;
import com.project.project_oop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(value -> CustomUserDetails.builder()
                .username(value.getUsername())
                .password(value.getPassword())
                .build()).orElse(null);
    }
}
