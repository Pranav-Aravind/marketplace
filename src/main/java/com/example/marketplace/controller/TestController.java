package com.example.marketplace.controller;
import com.example.marketplace.OrderService;
import com.example.marketplace.ProductService;
import com.example.marketplace.UserService;
import com.example.marketplace.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/test")
    public String testDatabase() {

        Product product = new Product();
        product.setName("Fossil X12");
        product.setPrice(100);
        product.setImg("https://m.media-amazon.com/images/I/71Tg6yQIDaL._AC_SX679_.jpg");
        product.setCaseSize(42);
        product.setType("Analog");
        product.setWidth(20);
        product.setMaterial("Stainless Steel");
        product.setDescription("A stylish analog watch with stainless steel strap.");

        Product product1 = new Product();
        product1.setName("Fossil A8");
        product1.setPrice(100);
        product1.setImg("https://m.media-amazon.com/images/I/71SyDkxOOFL._AC_SX679_.jpg");
        product1.setCaseSize(40);
        product1.setType("Digital");
        product1.setWidth(18);
        product1.setMaterial("Leather");
        product1.setDescription("Modern digital watch with premium leather strap.");

        Product product2 = new Product();
        product2.setName("Fossil G13");
        product2.setPrice(100);
        product2.setImg("https://m.media-amazon.com/images/I/71nYFuDIWnL._AC_SX679_.jpg");
        product2.setCaseSize(44);
        product2.setType("Chronograph");
        product2.setWidth(22);
        product2.setMaterial("Silicone");
        product2.setDescription("Sport chronograph watch built for durability and comfort.");


        productService.wipe();
        productService.addProduct(product);
        productService.addProduct(product1);
        productService.addProduct(product2);

        userService.wipe();
        orderService.wipe();

        return "test";
    }
}