package com.example.marketplace.controller;
import com.example.marketplace.ProductService;
import com.example.marketplace.model.Order;
import com.example.marketplace.model.User;
import com.example.marketplace.repository.OrderRepository;
import com.example.marketplace.repository.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/admin/products")
    public String admindashboard(HttpServletRequest request, org.springframework.ui.Model model) {

        HttpSession session = request.getSession(false);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("user");
        }
        if (!user.isAdmin()) {
            return "redirect:/";
        }

        model.addAttribute("user", user);
        model.addAttribute("products", productRepository.findAll());

        return "admindashboard";
    }

    @GetMapping("/admin/products/new")
    public String addProduct(HttpServletRequest request, org.springframework.ui.Model model) {

        HttpSession session = request.getSession(false);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("user");
        }
        if (!user.isAdmin()) {
            return "redirect:/";
        }

        model.addAttribute("user", user);

        return "createproduct";
    }

    @GetMapping("/admin/customerorders")
    public String customerOrders(HttpServletRequest request, org.springframework.ui.Model model) {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        User user = (User) session.getAttribute("user");
        if (!user.isAdmin()) {
            return "redirect:/";
        }

        List<Order> orderedItems = orderRepository.findByStatusNot("cart");

        model.addAttribute("user", user);
        model.addAttribute("orderedItems", orderedItems);

        return "customerorders";
    }

    @PostMapping("/admin/customerorders")
    public String orderhistory(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session == null) {
            return "redirect:/login";
        }

        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        return "redirect:/admin/customerorders";
    }

    @PostMapping("/admin/customerorders/updateStatus")
    public String updateStatus(@RequestParam int orderId,
                               @RequestParam String status,
                               HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        User user = (User) session.getAttribute("user");

        if (!user.isAdmin()) {
            return "redirect:/";
        }

        Order order = orderRepository.findById(orderId).orElseThrow();

        order.setStatus(status);
        orderRepository.save(order);

        return "redirect:/admin/customerorders";
    }
}