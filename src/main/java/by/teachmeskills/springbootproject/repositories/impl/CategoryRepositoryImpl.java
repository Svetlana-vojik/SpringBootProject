package by.teachmeskills.springbootproject.repositories.impl;

import by.teachmeskills.springbootproject.entities.Category;
import by.teachmeskills.springbootproject.repositories.CategoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
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
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public Category update(Category entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void delete(Category entity) {
        entityManager.remove(entity);
    }

    @Override
    public List<Category> read() {
        return entityManager.createQuery("from Category", Category.class).getResultList();
    }

    @Override
    public Category findNameById(int id) {
        return entityManager.find(Category.class, id);
    }
    @Override
    public Category findByName(String name) {
        return (Category) entityManager.createQuery("select c from Category c where c.name =:name").setParameter("name", name).getSingleResult();
    }
}