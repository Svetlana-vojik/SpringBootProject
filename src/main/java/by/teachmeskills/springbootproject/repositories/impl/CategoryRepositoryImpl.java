package by.teachmeskills.springbootproject.repositories.impl;

import by.teachmeskills.springbootproject.entities.Category;
import by.teachmeskills.springbootproject.repositories.CategoryRepository;
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
public class CategoryRepositoryImpl implements CategoryRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Category create(Category entity) {
        Session session = entityManager.unwrap(Session.class);
        session.persist(entity);
        return entity;
    }

    @Override
    public Category update(Category entity) {
        Session session = entityManager.unwrap(Session.class);
        return session.merge(entity);
    }

    @Override
    public void delete(Category entity) {
        Session session = entityManager.unwrap(Session.class);
        Category category = session.get(Category.class, entity);
        session.remove(category);
    }

    @Override
    public List<Category> read() {
        Session session = entityManager.unwrap(Session.class);
        Query<Category> query = session.createQuery("from Category", Category.class);
        return query.list();
    }

    @Override
    public Category findNameById(int id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Category.class, id);
    }
}