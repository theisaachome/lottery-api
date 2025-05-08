package com.highway.lottery.controller;

import com.highway.lottery.domain.dto.SignInDTO;
import com.highway.lottery.domain.dto.SignUpDTO;
import com.highway.lottery.service.AuthService;
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
    public ResponseEntity<String> signingIn(@RequestBody SignInDTO dto){
        var response = authService.signIn(dto);
        return  new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody SignUpDTO dto){
        var response = authService.register(dto);
      return  new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
