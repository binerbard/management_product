package com.management.product.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JWTUtils jwtUtils;

    private final AdminDetailService adminDetailService;

    public JwtAuthFilter(JWTUtils jwtUtils, AdminDetailService adminDetailService){
        this.jwtUtils = jwtUtils;
        this.adminDetailService = adminDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        String token = null;
        String username =null;

        if(header != null && header.startsWith("Bearer ")){

            token = header.substring(7);
            try {
                username = jwtUtils.getUsernameFromJWTToken(token);
            }catch (JwtException e){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
                return;
            }
        }
        log.info(username);
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            var userDetails = adminDetailService.loadUserByUsername(username);
            if(jwtUtils.validationJWTToken(token)){
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request,response);
    }
}
