package com.highway.lottery.config.security;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.highway.lottery.common.dto.ErrorDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        var errorDetails = new ErrorDetails();
        errorDetails.setDetails(authException.getMessage());
        errorDetails.setMessage(authException.getMessage());
        errorDetails.setStatusCode(HttpServletResponse.SC_UNAUTHORIZED);
        errorDetails.setTimestamp(LocalDateTime.now());

        var mapper = new ObjectMapper().registerModule(new JavaTimeModule());;
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
        response.getWriter().write(mapper.writeValueAsString(errorDetails));
    }
}
