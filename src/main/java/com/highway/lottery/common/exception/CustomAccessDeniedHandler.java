package com.highway.lottery.common.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.highway.lottery.common.dto.ErrorDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
       var errorDetails = new ErrorDetails();
        errorDetails.setTimestamp(LocalDateTime.now());
        errorDetails.setMessage("Access is denied");
        errorDetails.setDetails("uri=" + request.getRequestURI());
        errorDetails.setStatusCode(HttpServletResponse.SC_FORBIDDEN); // 403

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        String json = objectMapper.writeValueAsString(errorDetails);
        response.getWriter().write(json);

    }
}
