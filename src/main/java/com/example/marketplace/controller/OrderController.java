package com.example.marketplace.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OrderController {
    @ResponseBody
    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }

    @ResponseBody
    @GetMapping("/orderhistory")
    public String orderHistory() {
        return "orderhistory";
    }
}