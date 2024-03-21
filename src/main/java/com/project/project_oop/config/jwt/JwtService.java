package com.project.project_oop.config.jwt;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.duration}")
    private Long duration;

    private static final String AUTH_HEADER = "Authorization";

    public String extractTokenFroRequest(HttpServletRequest request) {
        String tokenBearer = request.getHeader(AUTH_HEADER);
        if (tokenBearer != null && tokenBearer.startsWith("Bearer ")) {
            return tokenBearer.substring(7);
        }
        return "";
    }

}
