package by.teachmeskills.springbootproject.repositories.impl;

import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
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
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public List<User> read() {
        return entityManager.createQuery("select u from User u ", User.class).getResultList();
    }

    @Override
    public User update(User entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void delete(User entity) {
        entityManager.remove(entity);
    }

    @Override
    public User findById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        TypedQuery<User> query = entityManager.createQuery("select u from User u where u.email=:email and u.password=:password", User.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        return query.getSingleResult();
    }
}