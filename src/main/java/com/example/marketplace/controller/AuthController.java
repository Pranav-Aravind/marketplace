package com.example.marketplace.controller;
import com.example.marketplace.UserService;
import com.example.marketplace.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegister() {
        return "register";
    }

    public String registerUser(@RequestParam String username,
                               @RequestParam String password) {

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setAdmin(false);

        userService.addUser(user);

        return "redirect:/login";
    }
}