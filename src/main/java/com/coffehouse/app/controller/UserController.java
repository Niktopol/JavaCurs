package com.coffehouse.app.controller;

import org.springframework.web.bind.annotation.*;

//@AllArgsConstructor
@RestController
@RequestMapping("/")
public class UserController {
    @GetMapping
    public String hello(){
        return "Hello";
    }
}
