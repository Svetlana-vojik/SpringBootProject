package by.teachmeskills.springbootproject.repositories.impl;

import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.entities.SearchWord;
import by.teachmeskills.springbootproject.repositories.ProductRepository;
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
public class ProductRepositoryImpl implements ProductRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Product create(Product entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public List<Product> read() {
        return entityManager.createQuery("select p from Product p where p.category.id=:categoryId", Product.class).getResultList();
    }

    @Override
    public Product update(Product entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void delete(Product entity) {
        entityManager.remove(entity);
    }

    @Override
    public Product findById(int id) {
        return entityManager.find(Product.class, id);
    }

    @Override
    public List<Product> getProductsByCategory(int categoryId) {
        TypedQuery<Product> query = entityManager.createQuery("select p from Product p where p.category.id=:categoryId", Product.class);
        query.setParameter("categoryId", categoryId);
        return query.getResultList();
    }

    @Override
    public List<Product> findProducts(SearchWord searchWord) {
        TypedQuery<Product> query = entityManager.createQuery("from Product where name like :search or description like :search", Product.class);
        query.setParameter("search", "%" + searchWord.getSearchString().toLowerCase() + "%");
        query.setFirstResult((searchWord.getPaginationNumber() - 1) * 2);
        query.setMaxResults(3);
        return query.getResultList();
    }
}