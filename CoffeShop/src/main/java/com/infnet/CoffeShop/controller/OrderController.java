package com.infnet.CoffeShop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coffee-shop")
public class OrderController {
    @GetMapping
    public String teste(){
        return "OK";
    }
}
