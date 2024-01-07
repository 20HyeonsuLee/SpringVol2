package com.example.BoardAPI.interceptor;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class JwtTokenInterceptor implements HandlerInterceptor {
    private static final String SECRET_KEY = "c3ByaW5nYm9vdC1qd3QtdHV0b3JpYWwtc3ByaW5nYm9vdC1qd3QtdHV0b3JpYWwtc3ByaW5nYm9vdC1qd3QtdHV0b3JpYWwK";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is missing or invalid.");
            return false;
        }else {
            token = token.substring(7);
            try {
                Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            } catch (Exception e) {
                throw new IllegalStateException("Invalid Token. " + e.getMessage());
            }
        }
        return true;
    }
}
