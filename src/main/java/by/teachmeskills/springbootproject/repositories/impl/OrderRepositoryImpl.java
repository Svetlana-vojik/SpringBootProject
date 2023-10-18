package by.teachmeskills.springbootproject.repositories.impl;

import by.teachmeskills.springbootproject.entities.Category;
import by.teachmeskills.springbootproject.entities.Order;
import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.repositories.OrderRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    private final JdbcTemplate jdbcTemplate;
    private final static Logger log = LogManager.getLogger(CategoryRepositoryImpl.class);
    private static final String CREATE_ORDER = "INSERT INTO shop.orders(orderDate,price,userId";
    private static final String GET_ALL_ORDERS = "SELECT * FROM shop.orders";
    private static final String DELETE_ORDER = "DELETE FROM shop.orders WHERE id=?";
    private static final String UPDATE_ORDER = "UPDATE shop.orders WHERE id = ?";
    private static final String GET_ORDER = "SELECT * FROM shop.orders WHERE id=?";
    private static final String GET_ORDER_BY_USER_ID = "SELECT * FROM shop.orders WHERE userId=?";

    public OrderRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Order create(Order entity) {
        jdbcTemplate.update(CREATE_ORDER, Timestamp.valueOf(entity.getOrderDate()), entity.getPrice(), entity.getUserId());
        return entity;
    }

    @Override
    public List<Order> read() {
        return jdbcTemplate.query(GET_ALL_ORDERS, new BeanPropertyRowMapper<>(Order.class));
    }

    @Override
    public Order update(Order entity) {
        jdbcTemplate.update(UPDATE_ORDER, entity.getId());
        return entity;
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_ORDER, id);
    }


    @Override
    public Order findById(int id) {
        return jdbcTemplate.queryForObject(GET_ORDER, new BeanPropertyRowMapper<>(Order.class), id);
    }

    @Override
    public List<Order> findByUserId(int id) {
        return jdbcTemplate.query(GET_ORDER_BY_USER_ID, new BeanPropertyRowMapper<>(Order.class), id);
    }
}