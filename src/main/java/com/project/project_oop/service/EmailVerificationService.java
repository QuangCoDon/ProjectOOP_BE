package com.project.project_oop.service;

import com.project.project_oop.model.User;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface EmailVerificationService {

    String generateEmailVerificationToken(User user);

    boolean verify(String emailVerificationToken);

//    void sendVerificationEmail(UserDTO user);

     boolean sendVerificationEmail(Long id, String address, String fullName, String otp);

}
