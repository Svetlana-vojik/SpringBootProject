package by.teachmeskills.springbootproject.repositories.impl;

import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.entities.SearchWord;
import by.teachmeskills.springbootproject.repositories.ProductRepository;
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
public class ProductRepositoryImpl implements ProductRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Product create(Product entity) {
        Session session = entityManager.unwrap(Session.class);
        session.persist(entity);
        return entity;
    }

    @Override
    public List<Product> read() {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("select p from Product p where p.category.id=:categoryId", Product.class).list();
    }

    @Override
    public Product update(Product entity) {
        Session session = entityManager.unwrap(Session.class);
        return session.merge(entity);
    }

    @Override
    public void delete(Product entity) {
        Session session = entityManager.unwrap(Session.class);
        session.remove(entity);
    }

    @Override
    public Product findById(int id) {
        return entityManager.find(Product.class, id);
    }

    @Override
    public List<Product> findByCategoryId(int id) {
        Session session = entityManager.unwrap(Session.class);
        Query<Product> query = session.createQuery("select p from Product p where p.category.id=:categoryId", Product.class);
        query.setParameter("categoryId", id);
        return query.list();
    }

    @Override
    public List<Product> findProductsByWord(SearchWord searchWord) {
        Session session = entityManager.unwrap(Session.class);
        Query<Product> query = session.createQuery( "from Product where name like :search or description like :search", Product.class);
        query.setParameter("search", "%" + searchWord.getSearchString().toLowerCase() + "%");
        query.setFirstResult((searchWord.getPaginationNumber() - 1) * 2);
        query.setMaxResults(2);
        return query.list();
    }
}