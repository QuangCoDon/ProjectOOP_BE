package com.project.project_oop.service.impl;

import com.project.project_oop.config.security.jwt.JwtService;
import com.project.project_oop.config.security.user.CustomUserDetails;
import com.project.project_oop.constant.Status;
import com.project.project_oop.model.Token;
import com.project.project_oop.model.User;
import com.project.project_oop.payload.request.LoginRequest;
import com.project.project_oop.payload.request.RegisterRequest;
import com.project.project_oop.payload.response.AuthResponse;
import com.project.project_oop.repository.TokenRepository;
import com.project.project_oop.repository.UserRepository;
import com.project.project_oop.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public AuthResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .createdAt(new Date())
                .status(Status.NOT_VERIFIED)
                .build();
        var savedUser = userRepository.save(user);
        CustomUserDetails userDetails = CustomUserDetails.builder()
                .username(savedUser.getUsername())
                .password(savedUser.getPassword())
                .build();
        var jwt = jwtService.buildToken(userDetails);
        saveUserToken(savedUser, jwt);
        return AuthResponse.builder()
                .accessToken(jwt)
                .refreshToken("hehe")
                .build();
    }
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername());
        if (user != null) {
            CustomUserDetails userDetails = CustomUserDetails.builder()
                    .username(request.getUsername())
                    .password(request.getPassword())
                    .build();
            var jwtToken = jwtService.buildToken(userDetails);
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);
            return AuthResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken("hehe")
                    .build();
        }
        return AuthResponse.builder().build();
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

}
