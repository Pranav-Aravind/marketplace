package com.example.marketplace.controller;
import com.example.marketplace.ProductService;
import com.example.marketplace.model.Product;
import com.example.marketplace.repository.ProductRepository;
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
    public String home(org.springframework.ui.Model model) {

        model.addAttribute("products", productRepository.findAll());

        return "home";
    }

    @GetMapping("/productdetails/{id}")
    public String prodDetails(@PathVariable int id, org.springframework.ui.Model model) {

        Product product = productService.findProduct(id);

        model.addAttribute("product", product);

        return "productdetails";
    }
}