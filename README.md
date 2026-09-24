# Online Watch Marketplace

A full stack online marketplace for watches, built with Java, Spring Boot, Thymeleaf, and H2.

## Features

### Customer

* **Registration and Login**: Create an account and log in as a customer.
* **Product Catalogue**: Browse a catalogue of watches with product images, prices, and details.
* **Product Details**: View individual watch information and add products to the basket.
* **Shopping Basket**: Add products, adjust quantities, remove items, and view item prices and total cost.
* **Checkout**: Place simulated orders without real payment processing.
* **Order History**: View previously placed orders with their original purchase prices.

### Administrator

* **Product Management**: Create, update, and hide watches from the catalogue.
* **Price Management**: Update product prices while preserving the original prices of historical orders.
* **Order Management**: View customer orders and update their status.
* **Role Based Access**: Separate customer and administrator functionality and views.

## Tech Stack

* **Backend**: Java, Spring Boot
* **Frontend**: HTML, Thymeleaf, CSS
* **Database**: H2
* **Build**: Maven
