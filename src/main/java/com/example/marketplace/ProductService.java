package com.example.marketplace;

import com.example.marketplace.model.Product;
import com.example.marketplace.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;


    @Transactional
    public void addProduct(Product prod) {
        productRepository.save(prod);
    }

    @Transactional
    public Product findProduct(@PathVariable int id) {
        return productRepository.findById(id).get();
    }

    @Transactional
    public void wipe() {
        productRepository.deleteAll();
    }
}
