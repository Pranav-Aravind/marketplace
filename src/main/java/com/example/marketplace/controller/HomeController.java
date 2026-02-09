package com.example.marketplace.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "Home";
    }

    @GetMapping("/")
    public String prodDetails() {
        return "ProductDetails";
    }

}