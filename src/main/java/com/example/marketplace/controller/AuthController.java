package com.example.marketplace.controller;
import com.example.marketplace.UserService;
import com.example.marketplace.model.User;
import com.example.marketplace.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
public class AuthController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


    @GetMapping("/register")
    public String showRegister(@RequestParam(required = false) Boolean admin,
                               Model model) {

        boolean isAdmin = admin != null && admin;
        model.addAttribute("isAdmin", isAdmin);

        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam(required = false) Boolean admin) {

        User user = new User();
        user.setUsername(username);

        String encoded = passwordEncoder.encode(password);
        user.setPassword(encoded);

        user.setAdmin(admin != null && admin);

        userService.addUser(user);

        return "redirect:/login";
    }


    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpServletRequest request) {

        User user = userRepository.findByUsername(username);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return "redirect:/login?error";
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        if(user.isAdmin()) {
            return "redirect:/admin/products";
        }
        return "redirect:/";
    }
}