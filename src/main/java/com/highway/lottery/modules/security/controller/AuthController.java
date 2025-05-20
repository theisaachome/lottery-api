package com.highway.lottery.modules.security.controller;

import com.highway.lottery.common.dto.JwtResponse;
import com.highway.lottery.common.dto.SignInDTO;
import com.highway.lottery.common.dto.SignUpDTO;
import com.highway.lottery.modules.security.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> signingIn(@RequestBody SignInDTO dto){
        var response = authService.signIn(dto);
        return  new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody SignUpDTO dto){
        var response = authService.register(dto);
      return  new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
