package com.example.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController 
@RequestMapping("/api/v1/test")
public class HelloController {
    @GetMapping("hello")
    public String helloWord(@RequestParam(value = "name", required = true) String name) {
        String message = String.format("Hello %s!", name);
        return message;
    }
    
}
