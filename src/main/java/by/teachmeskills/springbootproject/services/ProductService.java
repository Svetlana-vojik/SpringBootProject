package by.teachmeskills.springbootproject.services;

import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.entities.SearchWord;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
public interface ProductService extends BaseService<Product> {
    Product findById(int id);

    List<Product> findByCategoryId(int id);

    ModelAndView findProductsByWord(SearchWord searchWord);
}