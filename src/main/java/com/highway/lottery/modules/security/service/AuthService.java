package com.highway.lottery.modules.security.service;

import com.highway.lottery.common.dto.JwtResponse;
import com.highway.lottery.common.dto.SignInDTO;
import com.highway.lottery.common.dto.SignUpDTO;

public interface AuthService {
    JwtResponse signIn(SignInDTO dto);
    String  register(SignUpDTO dto);
}
