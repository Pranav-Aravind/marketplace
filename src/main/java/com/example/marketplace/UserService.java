package com.example.marketplace;

import com.example.marketplace.model.User;
import com.example.marketplace.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void addUser(User user) {
        userRepository.save(user);
    }

//    @Transactional
//    public Product findProduct(@PathVariable int id) {
//        return productRepository.findById(id).get();
//    }

    @Transactional
    public void wipe() {
        userRepository.deleteAll();
    }
}
