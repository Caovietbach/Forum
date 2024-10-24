package org.example.forum.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.forum.dto.AccountDTO;
import org.example.forum.entity.AccountEntity;
import org.example.forum.exception.ValidateException;
import org.example.forum.service.AuthenticationService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LoginFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    @Autowired
    private AuthenticationService authService;

    ModelMapper mapper = new ModelMapper();



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String jwtToken = request.getHeader("Authorization");
        logger.info("token: {}", jwtToken);

        if (jwtToken != null && authService.isTokenBlacklisted(jwtToken)) {
            setErrorResponse(response, request.getServletPath(), "Unauthorized", "Token is blacklisted");

        }
        if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
            String token = jwtToken.substring(7);
            logger.info("JWT: {}", token);
            try {
                AccountEntity user = authService.extractUser(token);
                logger.info("user: {}", user.getUsername());
                logger.info("The user role is: {}", user.getRole());

                if (user != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    List<GrantedAuthority> authorities = new ArrayList<>();
                    if ("user".equals(user.getRole())) {
                        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                    } else if ("admin".equals(user.getRole())) {
                        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                    } else {
                        throw new ValidateException("This user does not have a role.");
                    }
                    logger.info("The user has {} authority", authorities);

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    user,
                                    null,
                                    authorities
                            );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                setErrorResponse(response, request.getServletPath(), "Unauthorized", e.getMessage());
            }
        }


        chain.doFilter(request, response);
    }

    private void setErrorResponse(HttpServletResponse response, String path, String error, String message) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        Map<String, Object> body = new HashMap<>();
        body.put("path", path);
        body.put("error", error);
        body.put("message", message);
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }

}
