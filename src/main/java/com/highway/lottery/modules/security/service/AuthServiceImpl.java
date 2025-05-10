package com.highway.lottery.modules.security.service;
import com.highway.lottery.common.dto.JwtResponse;
import com.highway.lottery.common.dto.SignInDTO;
import com.highway.lottery.common.dto.SignUpDTO;
import com.highway.lottery.modules.account.entity.Account;
import com.highway.lottery.common.exception.APIException;
import com.highway.lottery.modules.account.repo.AccountRepo;
import com.highway.lottery.modules.security.repo.RoleRepository;
import com.highway.lottery.config.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService{
    private final AccountRepo accountRepo;
    private final PasswordEncoder   passwordEncoder;
    private final RoleRepository roleRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AccountRepo accountRepo, PasswordEncoder passwordEncoder, RoleRepository roleRepo, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.accountRepo = accountRepo;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public JwtResponse signIn(SignInDTO dto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.phone(),
                            dto.password())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenProvider.generateToken(authentication);
            var expiresIn = LocalDateTime.now().plusMinutes(7);
            return new JwtResponse(token,String.format("Expires: %s", expiresIn));
        } catch (Exception e) {
//            return "Login failed: " + e.getMessage(); // Log or return a proper error response
            e.printStackTrace();
            throw  new APIException(HttpStatus.UNAUTHORIZED,e.getMessage());
        }
    }

    @Override
    public String register(SignUpDTO dto) {
         // check if user already register with given phone
        if(accountRepo.existsAccountByPhone(dto.phone())){
            throw  new APIException(HttpStatus.BAD_REQUEST,"User already exist with  " + dto.phone());
        }
        var account = new Account();
        account.setPhone(dto.phone());
        account.setUsername(dto.username());
        account.setPassword(passwordEncoder.encode(dto.password()));
        // setup roles ->
        var roles = roleRepo.findByRoleName("ROLE_ADMIN")
                .orElseThrow(()-> new APIException(HttpStatus.BAD_REQUEST,"No role found"));
        account.setRoles(Set.of(roles));

        accountRepo.save(account);
        return "Account registered successfully";
    }
}
