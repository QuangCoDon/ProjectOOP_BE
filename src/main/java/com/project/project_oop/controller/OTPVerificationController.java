package com.project.project_oop.controller;

import com.project.project_oop.model.User;
import com.project.project_oop.service.EmailVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}" + "${api.version}" + "/otp")
public class OTPVerificationController {

    @Autowired
    private EmailVerificationService verificationService;

    private String generateOTPToken(User user) {
        return user.getUsername();
    }

//    @PostMapping("/verify")
//    public ResponseEntity<BaseResponse> verification(
//            @RequestParam(name = "code") String code
//    ) {
//
//    }
}
