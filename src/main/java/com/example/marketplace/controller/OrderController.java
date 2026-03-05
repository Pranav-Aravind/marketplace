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
import org.springframework.web.bind.annotation.PathVariable;
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

    private User getSessionUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return null;
        return (User) session.getAttribute("user");
    }

    @GetMapping("/cart")
    public String cart(HttpServletRequest request, org.springframework.ui.Model model) {

        User user = getSessionUser(request);
        if (user == null) return "redirect:/login";

        List<Order> cartItems = orderRepository.findByUserAndStatus(user, "cart");

        double total = 0;
        for (Order item : cartItems) {
            total += item.getPrice() * item.getQuantity();
        }

        model.addAttribute("user", user);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);

        return "cart";
    }

    @PostMapping("/cart")
    public String addToCart(@RequestParam int productId,
                            HttpServletRequest request) {

        User user = getSessionUser(request);
        if (user == null) return "redirect:/login";

        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) return "redirect:/";

        Order order = new Order();
        order.setUser(user);
        order.setProduct(product);
        order.setQuantity(1);
        order.setStatus("cart");
        order.setPrice(product.getPrice());

        orderService.addOrder(order);

        return "redirect:/cart";
    }

    @PostMapping("/cart/increase/{id}")
    public String increaseQuantity(@PathVariable int id, HttpServletRequest request) {

        User user = getSessionUser(request);
        if (user == null) return "redirect:/login";

        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            order.setQuantity(order.getQuantity() + 1);
            orderRepository.save(order);
        }

        return "redirect:/cart";
    }

    @PostMapping("/cart/decrease/{id}")
    public String decreaseQuantity(@PathVariable int id, HttpServletRequest request) {

        User user = getSessionUser(request);
        if (user == null) return "redirect:/login";

        Order order = orderRepository.findById(id).orElse(null);

        if (order != null) {
            if (order.getQuantity() > 1) {
                order.setQuantity(order.getQuantity() - 1);
                orderRepository.save(order);
            }
        }

        return "redirect:/cart";
    }

    @PostMapping("/cart/remove/{id}")
    public String removeCartItem(@PathVariable int id, HttpServletRequest request) {

        User user = getSessionUser(request);
        if (user == null) return "redirect:/login";

        orderRepository.deleteById(id);

        return "redirect:/cart";
    }

    @PostMapping("/cart/checkout")
    public String placeOrder(HttpServletRequest request) {

        User user = getSessionUser(request);
        if (user == null) return "redirect:/login";

        List<Order> cartItems = orderRepository.findByUserAndStatus(user, "cart");

        for (Order order : cartItems) {
            order.setStatus("placed");
            orderRepository.save(order);
        }

        return "redirect:/orderhistory";
    }


    @GetMapping("/orderhistory")
    public String orderhistory(HttpServletRequest request, org.springframework.ui.Model model) {
        User user = getSessionUser(request);
        if (user == null) return "redirect:/login";

        List<Order> orderedItems = orderRepository.findByUserAndStatus(user, "placed");

        model.addAttribute("user", user);
        model.addAttribute("orderedItems", orderedItems);

        return "orderhistory";
    }
}