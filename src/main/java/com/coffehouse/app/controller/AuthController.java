package com.coffehouse.app.controller;

import com.coffehouse.app.model.dto.UserDTO;
import com.coffehouse.app.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("auth/signup")
    public String signUp(@RequestBody UserDTO userData){
        if(authService.signUp(userData)){
            return "User created";
        }else{
            return "User exists";
        }
    }

    @PostMapping("auth/signin")
    public String signIn(@RequestBody UserDTO userData, HttpServletRequest request, HttpServletResponse response){
        if(authService.signIn(userData, request, response)){
            return "Signed in successfully";
        }else{
            return "Bad credentials";
        }
    }
}
