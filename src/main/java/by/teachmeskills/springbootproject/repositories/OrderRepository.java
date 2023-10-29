package by.teachmeskills.springbootproject.repositories;

import by.teachmeskills.springbootproject.entities.Order;
import by.teachmeskills.springbootproject.entities.User;

import java.util.List;

public interface OrderRepository extends BaseRepository<Order> {
    List<Order> findByUser(User id);
}