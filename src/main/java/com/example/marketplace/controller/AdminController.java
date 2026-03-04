package com.example.marketplace.controller;
import com.example.marketplace.ProductService;
import com.example.marketplace.model.Order;
import com.example.marketplace.model.Product;
import com.example.marketplace.model.User;
import com.example.marketplace.repository.OrderRepository;
import com.example.marketplace.repository.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductService productService;


    private User getAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return null;
        User user = (User) session.getAttribute("user");
        if (user == null || !user.isAdmin()) return null;
        return user;
    }


    @GetMapping("/admin/products")
    public String adminDashboard(HttpServletRequest request, Model model) {
        User user = getAdmin(request);
        if (user == null) return "redirect:/login";

        model.addAttribute("user", user);
        model.addAttribute("products", productRepository.findAll());

        return "admindashboard";
    }


    @GetMapping("/admin/products/new")
    public String addProduct(HttpServletRequest request, Model model) {
        User user = getAdmin(request);
        if (user == null) return "redirect:/login";

        model.addAttribute("user", user);
        model.addAttribute("product", new Product());

        return "addproduct";
    }


    @GetMapping("/admin/editproduct/{id}")
    public String editProduct(HttpServletRequest request, @PathVariable int id, Model model) {
        User user = getAdmin(request);
        if (user == null) return "redirect:/login";

        Product product = productService.findProduct(id);

        model.addAttribute("user", user);
        model.addAttribute("product", product);

        return "editproduct";
    }


    @PostMapping("/admin/deleteproduct/{id}")
    public String deleteProduct(HttpServletRequest request, @PathVariable int id) {
        User user = getAdmin(request);
        if (user == null) return "redirect:/login";

        productRepository.deleteById(id);

        return "redirect:/admin/products";
    }


    @PostMapping("/admin/products/save")
    public String saveProduct(HttpServletRequest request, @ModelAttribute Product product) {
        User user = getAdmin(request);
        if (user == null) return "redirect:/login";

        productRepository.save(product);

        return "redirect:/admin/products";
    }





    @GetMapping("/admin/customerorders")
    public String customerOrders(HttpServletRequest request, org.springframework.ui.Model model) {
        User user = getAdmin(request);
        if (user == null) return "redirect:/login";

        List<Order> orderedItems = orderRepository.findByStatusNot("cart");

        model.addAttribute("user", user);
        model.addAttribute("orderedItems", orderedItems);

        return "customerorders";
    }


    @PostMapping("/admin/customerorders/updateStatus")
    public String updateStatus(@RequestParam int orderId, @RequestParam String status, HttpServletRequest request) {

        User user = getAdmin(request);
        if (user == null) return "redirect:/login";
        Order order = orderRepository.findById(orderId).orElseThrow();

        order.setStatus(status);
        orderRepository.save(order);

        return "redirect:/admin/customerorders";
    }
}