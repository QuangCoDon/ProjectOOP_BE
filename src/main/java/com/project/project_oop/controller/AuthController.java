package com.project.project_oop.controller;

import com.project.project_oop.payload.request.LoginRequest;
import com.project.project_oop.payload.request.RegisterRequest;
import com.project.project_oop.payload.response.AuthResponse;
import com.project.project_oop.service.AuthService;
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
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(new AuthResponse("test", "test"));
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(new AuthResponse("test", "test"));
    }

//    @PostMapping("/refresh_token")
//    public ResponseEntity<AuthResponse>
}
