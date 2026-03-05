package com.example.marketplace.controller;
import com.example.marketplace.ProductService;
import com.example.marketplace.model.Product;
import com.example.marketplace.model.User;
import com.example.marketplace.repository.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    private User getSessionUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return null;
        return (User) session.getAttribute("user");
    }

    @GetMapping("/")
    public String home(@RequestParam(required = false) String type,
                       @RequestParam(required = false) Double minPrice,
                       @RequestParam(required = false) Double maxPrice,
                       HttpServletRequest request,
                       Model model) {

        User user = getSessionUser(request); // reuse your session helper
        model.addAttribute("user", user);

        List<Product> products = productRepository.findByActiveTrue();

        // Filter by type
        if (type != null && !type.isEmpty()) {
            products = products.stream()
                    .filter(p -> p.getType().equalsIgnoreCase(type))
                    .toList();
        }

        // Filter by minPrice
        if (minPrice != null) {
            products = products.stream()
                    .filter(p -> p.getPrice() >= minPrice)
                    .toList();
        }

        // Filter by maxPrice
        if (maxPrice != null) {
            products = products.stream()
                    .filter(p -> p.getPrice() <= maxPrice)
                    .toList();
        }

        model.addAttribute("products", products);

        List<String> types = productRepository.findByActiveTrue()
                .stream()
                .map(Product::getType)
                .distinct()
                .toList();

        model.addAttribute("types", types);

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