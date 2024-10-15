package org.example.forum.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.example.forum.filter.LoginFilter;
import org.example.forum.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthenticationController {

    @Autowired
    private AuthenticationService service;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);


    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletResponse response, Model model) {
        if(!service.validateLogin(username,password)){
            return "redirect:/login";
        };
        final String jwt = service.generateToken(username);


        Cookie cookie = new Cookie("JWT_TOKEN", jwt);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(10 * 60 * 60); // 8 hours
        cookie.setPath("/");
        response.addCookie(cookie);
        logger.info("Successfully logged in. Redirecting to Main Page.");
        return "redirect:/forum";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("JWT_TOKEN", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password){
        service.register(username,password);
        return "login";
    }


}
