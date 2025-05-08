package com.highway.lottery.security;

import com.highway.lottery.repository.AccountRepo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepo accountRepo;

    public CustomUserDetailsService(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrPhone) throws UsernameNotFoundException {
        var account = accountRepo.findAccountByPhone(usernameOrPhone)
                .orElseThrow(() -> new UsernameNotFoundException(usernameOrPhone));
        Set<GrantedAuthority> grantedAuthorities =
                account.getRoles()
                        .stream()
                        .map((role -> new SimpleGrantedAuthority(role.getRoleName())))
                        .collect(Collectors.toSet());
        return new SecurityUser(account);
    }
}
