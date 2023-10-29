package by.teachmeskills.springbootproject.repositories.impl;

import by.teachmeskills.springbootproject.entities.Order;
import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.repositories.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
@AllArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Order create(Order entity) {
        Session session = entityManager.unwrap(Session.class);
        session.persist(entity);
        return entity;
    }

    @Override
    public List<Order> read() {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("select o from Order o", Order.class).list();
    }

    @Override
    public Order update(Order entity) {
        Session session = entityManager.unwrap(Session.class);
        return session.merge(entity);
    }

    @Override
    public void delete(Order entity) {
        Session session = entityManager.unwrap(Session.class);
        session.remove(entity);
    }

    @Override
    public List<Order> findByUser(User user) {
        Session session = entityManager.unwrap(Session.class);
        Query<Order> query = session.createQuery( "select o from Order o where o.user=:user", Order.class);
        query.setParameter("user", user);
        return query.list();
    }
}