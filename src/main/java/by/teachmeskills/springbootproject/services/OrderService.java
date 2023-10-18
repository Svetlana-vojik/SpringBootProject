package by.teachmeskills.springbootproject.services;

import by.teachmeskills.springbootproject.entities.Order;

import java.util.List;

public interface OrderService extends BaseService<Order> {
    Order findById(int id);

    List<Order> findByUserId(int id);

    List<Order> getOrdersByUserId(int id);
}