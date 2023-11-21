package by.teachmeskills.springbootproject.repositories;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import by.teachmeskills.springbootproject.entities.Category;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findNameById(int id);

    Category findByName(String name);
}