package com.example.marketplace.controller;
import com.example.marketplace.ProductService;
import com.example.marketplace.model.Product;
import com.example.marketplace.model.User;
import com.example.marketplace.repository.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    public String home(HttpServletRequest request, org.springframework.ui.Model model) {

        HttpSession session = request.getSession(false);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("user");
        }
        model.addAttribute("user", user);
        model.addAttribute("products", productRepository.findByActiveTrue());

        return "home";
    }   

    @GetMapping("/productdetails/{id}")
    public String prodDetails(HttpServletRequest request, @PathVariable int id, org.springframework.ui.Model model) {

        HttpSession session = request.getSession(false);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("user");
        }

        model.addAttribute("user", user);
        Product product = productService.findProduct(id);

        model.addAttribute("product", product);

        return "productdetails";
    }
}