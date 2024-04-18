package com.project.project_oop.controller;

import com.project.project_oop.payload.request.auth.LoginRequest;
import com.project.project_oop.payload.request.auth.RegisterRequest;
import com.project.project_oop.payload.response.BaseResponse;
import com.project.project_oop.payload.response.auth.AuthResponse;
import com.project.project_oop.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}" + "${api.version}" + "/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh_token")
    public void refreshToken(
        HttpServletRequest request,
        HttpServletResponse response
    ) {

    }
}
