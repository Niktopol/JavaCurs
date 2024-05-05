package com.coffehouse.app.controller;

import com.coffehouse.app.model.dto.UserDTO;
import com.coffehouse.app.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public String signUp(@RequestBody UserDTO userData){
        if(authService.signUp(userData)){
            return "User created";
        }else{
            return "User exists";
        }
    }

    @PostMapping("/signin")
    public String signIn(@RequestBody UserDTO userData, HttpServletRequest request, HttpServletResponse response){
        return authService.signIn(userData, request, response);
    }
}
