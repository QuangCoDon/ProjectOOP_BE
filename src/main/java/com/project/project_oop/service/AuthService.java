package com.project.project_oop.service;

import com.project.project_oop.payload.request.LoginRequest;
import com.project.project_oop.payload.request.RegisterRequest;
import com.project.project_oop.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

}
