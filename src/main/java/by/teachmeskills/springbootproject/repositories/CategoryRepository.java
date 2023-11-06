package by.teachmeskills.springbootproject.repositories;

import by.teachmeskills.springbootproject.entities.Category;

public interface CategoryRepository extends BaseRepository<Category> {
    Category findNameById(int id);

    Category findByName(String name);
}