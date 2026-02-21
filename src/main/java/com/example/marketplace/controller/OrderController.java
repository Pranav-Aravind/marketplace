package com.example.marketplace.controller;
import com.example.marketplace.OrderService;
import com.example.marketplace.model.Order;
import com.example.marketplace.model.Product;
import com.example.marketplace.model.User;
import com.example.marketplace.repository.OrderRepository;
import com.example.marketplace.repository.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderService orderService;

    @GetMapping("/cart")
    public String cart(HttpServletRequest request, org.springframework.ui.Model model) {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        User user = (User) session.getAttribute("user");

        List<Order> cartItems = orderRepository.findByUserAndStatus(user, "cart");

        model.addAttribute("user", user);
        model.addAttribute("cartItems", cartItems);

        return "cart";
    }


    @PostMapping("/cart")
    public String cart(@RequestParam int productId,
                       HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session == null) {
            return "redirect:/login";
        }

        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return "redirect:/";
        }

        Order order = new Order();
        order.setUser(user);
        order.setProduct(product);
        order.setQuantity(1);
        order.setStatus("cart");

        orderService.addOrder(order);

        return "redirect:/cart";
    }


    @GetMapping("/orderhistory")
    public String orderhistory(HttpServletRequest request, org.springframework.ui.Model model) {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        User user = (User) session.getAttribute("user");

        List<Order> orderedItems = orderRepository.findByUserAndStatus(user, "placed");

        model.addAttribute("user", user);
        model.addAttribute("orderedItems", orderedItems);

        return "orderhistory";
    }

    @PostMapping("/orderhistory")
    public String orderhistory(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session == null) {
            return "redirect:/login";
        }

        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        List<Order> cartItems = orderRepository.findByUserAndStatus(user, "cart");

        for(Order item: cartItems) {
            item.setStatus("placed");
            orderRepository.save(item);
        }

        return "redirect:/orderhistory";
    }



}