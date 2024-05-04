package com.coffehouse.app.service;

import com.coffehouse.app.model.User;
import com.coffehouse.app.model.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import static com.coffehouse.app.model.User.Role.USER;


@Service
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final SecurityContextRepository securityContextRepository;
    private final AuthenticationManager authenticationManager;

    public boolean signUp(UserDTO userData) {

        User user = new User(userData.getUsername(), passwordEncoder.encode(userData.getPassword()), USER);

        return userService.create(user);
    }

    public boolean signIn(UserDTO userData, HttpServletRequest request, HttpServletResponse response) {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                userData.getUsername(), userData.getPassword());
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(token);
        }catch (AuthenticationException e){
            return false;
        }

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        securityContextRepository.saveContext(context, request, response);

        return true;
    }

    public AuthService(PasswordEncoder passwordEncoder, UserService userService,
                       AuthenticationManager authenticationManager){
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.securityContextRepository = new HttpSessionSecurityContextRepository();
        this.authenticationManager = authenticationManager;
    }
}
