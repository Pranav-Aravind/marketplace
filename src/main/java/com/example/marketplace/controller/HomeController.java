package com.example.marketplace.controller;
import com.example.marketplace.ProductService;
import com.example.marketplace.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/{id}")
    public String home(@PathVariable int id, org.springframework.ui.Model model) {

        Product product = productService.findProduct(id);

        model.addAttribute("product", product);

        return "productdetails";
    }

    @GetMapping("/productdetails/{id}")
    public String prodDetails(@PathVariable int id, org.springframework.ui.Model model) {

        Product product = productService.findProduct(id);

        model.addAttribute("product", product);

        return "productdetails";
    }
}