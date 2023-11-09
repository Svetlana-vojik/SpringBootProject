package by.teachmeskills.springbootproject.services;

import by.teachmeskills.springbootproject.entities.PaginationParams;
import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.AuthorizationException;
import org.springframework.web.servlet.ModelAndView;

public interface UserService extends BaseService<User> {
    User findById(int id);

    ModelAndView createUser(User user) throws AuthorizationException;

    ModelAndView authenticate(User user) throws AuthorizationException;

    ModelAndView userAccountPage(User user, PaginationParams paginationParams);

    User getCurrentUser();
}