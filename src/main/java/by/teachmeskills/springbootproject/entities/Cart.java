package by.teachmeskills.springbootproject.entities;


import by.teachmeskills.springbootproject.repositories.ProductRepository;
import by.teachmeskills.springbootproject.repositories.impl.ProductRepositoryImpl;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart extends BaseEntity{
    private Map<Integer, Product> products;
    private int totalPrice = 0;
    private final ProductRepository productRepository = new ProductRepositoryImpl(new JdbcTemplate());
    public Cart() {
        this.products = new HashMap<>();
    }

    public void addProduct(Product product) {
        products.put(product.getId(), product);
        totalPrice += product.getPrice();
    }

    public void removeProduct(int productId) {
        Product product = products.get(productId);
        products.remove(productId);
        totalPrice -= product.getPrice();
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products.values());
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void clear() {
        products.clear();
    }
}