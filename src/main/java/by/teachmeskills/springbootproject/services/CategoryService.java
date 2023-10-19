package by.teachmeskills.springbootproject.services;

import by.teachmeskills.springbootproject.entities.Category;
import org.springframework.web.servlet.ModelAndView;

public interface CategoryService extends BaseService<Category> {
    ModelAndView findNameById(int id);
}