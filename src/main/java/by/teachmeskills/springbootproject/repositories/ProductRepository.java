package by.teachmeskills.springbootproject.repositories;

import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.entities.SearchWord;

import java.util.List;

public interface ProductRepository extends BaseRepository<Product> {
    Product findById(int id);

    List<Product> findByCategoryId(int id);

    List<Product> findProductsByWord(SearchWord searchWord);
}