package com.example.marketplace.repository;

import com.example.marketplace.model.Order;
import com.example.marketplace.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserAndStatus(User user, String status);
}