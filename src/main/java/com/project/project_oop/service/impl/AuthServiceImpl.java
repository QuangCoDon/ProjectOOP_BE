package com.project.project_oop.service.impl;

import com.project.project_oop.config.security.jwt.JwtService;
import com.project.project_oop.config.security.user.CustomUserDetails;
import com.project.project_oop.constant.ErrorConstant;
import com.project.project_oop.constant.MessageConstant;
import com.project.project_oop.model.EmailVerificationToken;
import com.project.project_oop.model.constant.Status;
import com.project.project_oop.model.AuthToken;
import com.project.project_oop.model.User;
import com.project.project_oop.payload.request.auth.LoginRequest;
import com.project.project_oop.payload.request.auth.RegisterRequest;
import com.project.project_oop.payload.response.BaseResponse;
import com.project.project_oop.payload.response.auth.AuthResponse;
import com.project.project_oop.repository.AuthTokenRepository;
import com.project.project_oop.repository.EmailVerificationTokenRepository;
import com.project.project_oop.repository.UserRepository;
import com.project.project_oop.service.AuthService;
import com.project.project_oop.service.EmailVerificationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthTokenRepository authTokenRepository;

    @Autowired
    private EmailVerificationTokenRepository emailVerificationTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EmailVerificationService emailVerificationService;

    private void saveUserToken(User user, String jwtToken) {
        var token = AuthToken.builder()
                .user(user)
                .token(jwtToken)
                .expired(0L)
                .revoked(0L)
                .build();
        authTokenRepository.save(token);
    }

    @Override
    public BaseResponse register(RegisterRequest request) {
        var user = userRepository.findByUsername(request.getUsername());
        if (user.isPresent()) {
            return BaseResponse.builder()
                    .isError(ErrorConstant.USERNAME_DUPLICATE_CODE)
                    .errorMessage(ErrorConstant.USERNAME_DUPLICATE)
                    .build();
        } else {
            var tmp_user = User.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .email(request.getEmail())
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .createdAt(new Date())
                    .status(Status.NOT_VERIFIED)
                    .build();
            var saved_user = userRepository.save(tmp_user);
            var emailVerificationToken_tmp = EmailVerificationToken.builder()
                    .user(saved_user)
                    .token(emailVerificationService.generateEmailVerificationToken(saved_user))
                    .expired(0L)
                    .build();
            var saved_emailVerificationToken = emailVerificationTokenRepository.save(emailVerificationToken_tmp);
            boolean success = emailVerificationService.sendVerificationEmail(saved_user.getId(), saved_user.getEmail(), saved_user.getFullName(), saved_emailVerificationToken.getToken());
            return BaseResponse.builder()
                    .isError(success ? 0L : 1L)
                    .errorMessage(success ? MessageConstant.REGISTER_SUCCESS : ErrorConstant.EMAIL_SEND_FAILED)
                    .build();
        }
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername()).orElse(null);
        if (user != null) {
            if (user.getStatus() == Status.NOT_VERIFIED) {
                return AuthResponse.builder()
                        .isError(ErrorConstant.ACCOUNT_NOT_VERIFIED_CODE)
                        .errorMessage(ErrorConstant.ACCOUNT_NOT_VERIFIED)
                        .build();
            } else {
                CustomUserDetails userDetails = CustomUserDetails.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .build();
                var jwtToken = jwtService.buildToken(userDetails);
//                var refreshToken = jwtService.generateRefreshToken(user);
                revokeAllUserTokens(user.getId());
                saveUserToken(user, jwtToken);
                return AuthResponse.builder()
                        .accessToken(jwtToken)
                        .refreshToken("hehe")
                        .build();
            }
        } else {
            return AuthResponse.builder()
                    .isError(ErrorConstant.USERNAME_PASSWORD_WRONG_CODE)
                    .errorMessage(ErrorConstant.USERNAME_PASSWORD_WRONG)
                    .build();
        }
    }

    private void revokeAllUserTokens(Long id) {
        var validUserTokens = authTokenRepository.findAllValidTokenByUser(id);
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(1L);
            token.setRevoked(1L);
        });
        authTokenRepository.saveAll(validUserTokens);
    }

    @Override
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {

    }

}
