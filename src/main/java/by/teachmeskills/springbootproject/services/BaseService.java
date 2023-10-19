package by.teachmeskills.springbootproject.services;

import by.teachmeskills.springbootproject.entities.BaseEntity;
import by.teachmeskills.springbootproject.entities.Category;
import by.teachmeskills.springbootproject.exceptions.AuthorizationException;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface BaseService<T extends BaseEntity> {
    ModelAndView create(T entity) throws AuthorizationException;

    List<T> read();

    T update(T entity);

    void delete(int id);
}