package by.teachmeskills.springbootproject.repositories.impl;

import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.repositories.UserRepository;
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
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public User create(User entity) {
        Session session = entityManager.unwrap(Session.class);
        session.persist(entity);
        return entity;
    }

    @Override
    public List<User> read() {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("select u from User u ", User.class).list();
    }

    @Override
    public User update(User entity) {
        Session session = entityManager.unwrap(Session.class);
        return session.merge(entity);
    }

    @Override
    public void delete(User entity) {
        Session session = entityManager.unwrap(Session.class);
        session.remove(entity);
    }

    @Override
    public User findById(int id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(User.class, id);
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        Session session = entityManager.unwrap(Session.class);
        Query<User> query = session.createQuery("select u from User u where u.email=:email and u.password=:password", User.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        return query.uniqueResult();
    }
}