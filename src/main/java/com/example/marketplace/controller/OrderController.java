package com.example.marketplace.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class OrderController {
    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }

    @GetMapping("/orderhistory")
    public String orderHistory() {
        return "orderhistory";
    }
}