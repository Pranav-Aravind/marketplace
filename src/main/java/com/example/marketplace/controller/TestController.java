package com.example.marketplace.controller;
import com.example.marketplace.model.Product;
import com.example.marketplace.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    private final ProductRepository productRepository;

    public TestController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/test")
    public String testDatabase() {
        Product product = new Product();
        product.setName("Test Watch");
        product.setPrice(100);
        product.setImg("https://m.media-amazon.com/images/I/71Tg6yQIDaL._AC_SX679_.jpg");

        Product product1 = new Product();
        product1.setName("Best Watch");
        product1.setPrice(100);
        product1.setImg(" https://m.media-amazon.com/images/I/71SyDkxOOFL._AC_SX679_.jpg");

        Product product2 = new Product();
        product2.setName("Over Watch");
        product2.setPrice(100);
        product2.setImg("https://m.media-amazon.com/images/I/71nYFuDIWnL._AC_SX679_.jpg");

        // Save it to the database
        //productRepository.save(product);
        productRepository.save(product1);
        productRepository.save(product2);
    // Create a Product
    // Use the repository method save() to persist it in the database
        return "test";
    }
}