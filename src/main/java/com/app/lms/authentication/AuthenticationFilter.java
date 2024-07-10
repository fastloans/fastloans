package com.app.lms.authentication;

import com.app.lms.dto.AuthenticatedUser;
import com.app.lms.dto.ErrorResponse;
import com.app.lms.util.Json;
import com.app.lms.util.JwtUtils;
import com.app.lms.util.enums.CreatedBy;
import com.app.lms.util.enums.UserType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Value("${spring.public.urls}")
    private List<AntPathRequestMatcher> publicUrls;

    AuthenticationFilter(JwtUtils jwtUtils, List<String> publicUrls){
        this.jwtUtils=jwtUtils;
        this.publicUrls=publicUrls.stream().map(AntPathRequestMatcher::new).toList();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        for(AntPathRequestMatcher publiUrl : publicUrls){
            if(publiUrl.matches(request)){
                filterChain.doFilter(request,response);
                return;
            }
        }
        String token = request.getHeader("X-AUTH-TOKEN");
        if(token==null || token.isBlank()){
            response.setContentType("application/json");
            int statusCode = HttpStatus.FORBIDDEN.value();
            ErrorResponse<Void> error = ErrorResponse.<Void>builder()
                    .errorMessage("Access Denied")
                    .developerMessage("Access Denied")
                    .statusCode(statusCode)
                    .build();
            response.setStatus(statusCode);
            response.getWriter().write(Json.dumps(error));
            return;
        }
        AuthenticatedUser user = jwtUtils.validateAndGetAuthenticatedUser(token);
        if(user==null) {
            response.setContentType("application/json");
            int statusCode = HttpStatus.UNAUTHORIZED.value();
            ErrorResponse<Void> error = ErrorResponse.<Void>builder()
                    .errorMessage("Unauthorized")
                    .developerMessage("Unauthorized")
                    .statusCode(statusCode)
                    .build();
            response.setStatus(statusCode);
            response.getWriter().write(Json.dumps(error));
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(user);
        filterChain.doFilter(request,response);
    }
}
