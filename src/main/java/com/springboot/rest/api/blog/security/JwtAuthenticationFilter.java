package com.springboot.rest.api.blog.security;

import com.springboot.rest.api.blog.model.User;
import com.springboot.rest.api.blog.service.UserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        extractTokenFromRequest(request)
            .map(token -> jwtUtils.extractUsername(token))
            .ifPresent(username -> {
                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    User user = (User) userDetailsService.loadUserByUsername(username);
                    UserAuthenticationToken userAuthenticationToken = new UserAuthenticationToken(user);
                    SecurityContextHolder.getContext().setAuthentication(userAuthenticationToken);
                }
            }
                
        );

        filterChain.doFilter(request, response);
    }
    
    private Optional<String> extractTokenFromRequest(HttpServletRequest request) {
        
        String token = request.getHeader("Authorization");
        return StringUtils.isNotBlank(token) && token.startsWith("Bearer ") ?
            Optional.of(token.substring(7)) :
            Optional.empty();

    }
    
}
