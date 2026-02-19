package com.example.marketplace;

import com.example.marketplace.model.Order;
import com.example.marketplace.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public void addOrder(Order order) {
        orderRepository.save(order);
    }


    @Transactional
    public void wipe() {
        orderRepository.deleteAll();
    }
}
