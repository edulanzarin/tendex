package br.com.tendex.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
@CrossOrigin(origins = "*")
public class TestController {

    @GetMapping
    public String hello() {
        return "Hello World";
    }
}