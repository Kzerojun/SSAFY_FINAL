package com.enjoytrip.security.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.enjoytrip.jwt.service.JwtTokenExtractor;
import com.enjoytrip.jwt.service.JwtTokenValidator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenValidator jwtTokenValidator;
    private final JwtTokenExtractor jwtTokenExtractor;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenExtractor.resolveToken(request);

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        DecodedJWT decodedJWT = jwtTokenValidator.validate(token);

        if (decodedJWT == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String username = decodedJWT.getSubject();
        String role = decodedJWT.getClaim("roles").asString();

        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(role)
        );

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                username,
                null,
                authorities
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }


}
