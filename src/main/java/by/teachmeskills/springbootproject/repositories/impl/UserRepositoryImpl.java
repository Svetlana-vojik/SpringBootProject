package by.teachmeskills.springbootproject.repositories.impl;

import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final String ADD_USER = "INSERT INTO shop.users (email,password,name,surname,birthday,balance,address) values (?,?,?,?,?,?,?)";
    private final static String GET_ALL_USERS = "SELECT * FROM  shop.users";
    private final static String UPDATE_ADDRESS = "UPDATE  shop.users SET address = ? WHERE id = ?";
    private final static String DELETE_USER = "DELETE FROM shop.users WHERE id=?";
    private static final String GET_USER_BY_ID = "SELECT * FROM shop.users WHERE id=?";
    private final static String GET_USER_BY_EMAIL_AND_PASSWORD = "SELECT * FROM shop.users WHERE email=? and password=?";

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User create(User entity) {
        jdbcTemplate.update(ADD_USER, entity.getEmail(), entity.getPassword(), entity.getName(),
                entity.getSurname(), entity.getBirthday(), entity.getBalance(), entity.getAddress());
        return entity;
    }

    @Override
    public List<User> read() {
        return jdbcTemplate.query(GET_ALL_USERS, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public User update(User entity) {
        jdbcTemplate.update(UPDATE_ADDRESS, entity.getAddress(), entity.getId());
        return entity;
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_USER, id);
    }

    @Override
    public User findById(int id) {
        return jdbcTemplate.queryForObject(GET_USER_BY_ID, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        return jdbcTemplate.query(GET_USER_BY_EMAIL_AND_PASSWORD, new BeanPropertyRowMapper<>(User.class),
                email, password).stream().findAny().orElse(null);
    }
}