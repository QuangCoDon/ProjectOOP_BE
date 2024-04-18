package com.project.project_oop.service;

import com.project.project_oop.payload.request.auth.LoginRequest;
import com.project.project_oop.payload.request.auth.RegisterRequest;
import com.project.project_oop.payload.response.BaseResponse;
import com.project.project_oop.payload.response.auth.AuthResponse;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;

public interface AuthService {

    BaseResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    void refreshToken(HttpServletRequest request, HttpServletResponse response);

}
