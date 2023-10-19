package by.teachmeskills.springbootproject.repositories.impl;

import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.repositories.ProductRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final String ADD_PRODUCT = "INSERT INTO shop.products(name, description, price, categoryId, imagePath) VALUES(?,?,?,?,?)";
    private static final String DELETE_PRODUCT = "DELETE FROM shop.products WHERE id=?";
    private static final String GET_ALL_PRODUCTS = "SELECT * FROM shop.products";
    private static final String GET_PRODUCT = "SELECT * FROM shop.products WHERE id=?";
    private static final String GET_CATEGORY_PRODUCTS = "SELECT * FROM shop.products WHERE categoryId=?";
    private final static String UPDATE_DESCRIPTION_AND_PRICE_BY_ID = "UPDATE shop.products SET description = ?, price = ? WHERE id = ?";
    private static final String GET_PRODUCTS_BY_WORD = "SELECT * FROM shop.products WHERE name LIKE ? OR description LIKE ? ORDER BY name ASC";

    public ProductRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Product create(Product entity) {
        jdbcTemplate.update(ADD_PRODUCT, entity.getName(), entity.getDescription(),
                entity.getPrice(), entity.getCategoryId(), entity.getImagePath());
        return entity;
    }

    @Override
    public List<Product> read() {
        return jdbcTemplate.query(GET_ALL_PRODUCTS, new BeanPropertyRowMapper<>(Product.class));
    }


    @Override
    public Product update(Product entity) {
        jdbcTemplate.update(UPDATE_DESCRIPTION_AND_PRICE_BY_ID, entity.getDescription(), entity.getPrice(), entity.getId());
        return entity;
    }


    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_PRODUCT, id);
    }

    @Override
    public Product findById(int id) {
        return jdbcTemplate.queryForObject(GET_PRODUCT, new BeanPropertyRowMapper<>(Product.class), id);
    }

    @Override
    public List<Product> findByCategoryId(int id) {
        return jdbcTemplate.query(GET_CATEGORY_PRODUCTS, new BeanPropertyRowMapper<>(Product.class), id);
    }

    @Override
    public List<Product> findProductsByWord(String search) {
        search = "%" + search.trim() + "%";
        return jdbcTemplate.query(GET_PRODUCTS_BY_WORD, new BeanPropertyRowMapper<>(Product.class), search, search);
    }
}