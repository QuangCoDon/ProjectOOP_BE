package com.project.project_oop.service.impl;

import com.project.project_oop.model.Token;
import com.project.project_oop.repository.TokenRepository;
import com.project.project_oop.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public Token findbyToken(String token) {
        return tokenRepository.findByToken(token);
    }
}
