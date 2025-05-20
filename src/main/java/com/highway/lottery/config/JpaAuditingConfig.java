package com.highway.lottery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfig {


    @Bean
    public AuditorAware<String> auditorProvider() {
        return ()->{
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication == null || !authentication.isAuthenticated() ||
            authentication.getPrincipal().equals("anonymousUser") ){
                return Optional.of("system");
            }
            return Optional.of(authentication.getName());
//           return Optional.of("System");
        };
    }
}
