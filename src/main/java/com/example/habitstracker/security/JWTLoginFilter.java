package com.example.habitstracker.security;

import com.example.habitstracker.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// TODO переделать на БИН
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
    private AuthenticationManager authenticationManager;
    private UserRepository userService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public JWTLoginFilter(String url, AuthenticationManager authenticationManager, UserRepository userService) {
        super(new AntPathRequestMatcher(url));
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        var credentials = objectMapper.readValue(request.getInputStream(), AccountCredentials.class);
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            credentials.username(), credentials.password(), List.of())
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        TokenAuthenticationService.getInstance().addAuthentication(response, authResult.getName(), userService.findByNickname(authResult.getName()).get().getUserId());
    }
}