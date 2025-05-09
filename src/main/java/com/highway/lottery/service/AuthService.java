package com.highway.lottery.service;

import com.highway.lottery.domain.dto.JwtResponse;
import com.highway.lottery.domain.dto.SignInDTO;
import com.highway.lottery.domain.dto.SignUpDTO;

public interface AuthService {
    JwtResponse signIn(SignInDTO dto);
    String  register(SignUpDTO dto);
}
