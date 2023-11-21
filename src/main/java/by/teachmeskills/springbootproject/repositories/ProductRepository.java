package by.teachmeskills.springbootproject.repositories;

import by.teachmeskills.springbootproject.entities.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    Product findById(int id);

    List<Product> findByCategoryId(int id);

    Page<Product> findByCategoryId(int categoryId, Pageable page);
}