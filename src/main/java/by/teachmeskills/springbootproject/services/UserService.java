package by.teachmeskills.springbootproject.services;

import by.teachmeskills.springbootproject.entities.User;
import org.springframework.web.servlet.ModelAndView;

public interface UserService extends BaseService<User> {
    User findById(int id);

    User findByEmailAndPassword(String email, String password);

    ModelAndView authenticate(User user);

    ModelAndView generateAccountPage(User user);

    ModelAndView createUser(User entity);
}