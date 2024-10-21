package org.example.forum.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.forum.dto.AccountDTO;
import org.example.forum.entity.AccountEntity;
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
            throws ServletException, IOException {
        String jwt = null;
        AccountEntity user = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JWT_TOKEN")) {
                    jwt = cookie.getValue();
                    break;
                }
            }
        }

        if (jwt != null) {
            user = authService.extractUser(jwt);
        }

        if (user != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            logger.info(user.getRole());
            List<GrantedAuthority> authorities = new ArrayList<>();
            if ("user".equals(user.getRole())) {
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            }
            if ("admin".equals(user.getRole())) {
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            authorities
                    );
            SecurityContextHolder.getContext().setAuthentication(authentication);
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
