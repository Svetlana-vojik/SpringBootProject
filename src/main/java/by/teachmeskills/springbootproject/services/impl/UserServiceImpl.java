package by.teachmeskills.springbootproject.services.impl;

import by.teachmeskills.springbootproject.PagesPathEnum;
import by.teachmeskills.springbootproject.entities.Category;
import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.AuthorizationException;
import by.teachmeskills.springbootproject.repositories.UserRepository;
import by.teachmeskills.springbootproject.services.CategoryService;
import by.teachmeskills.springbootproject.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

import static by.teachmeskills.springbootproject.PagesPathEnum.HOME_PAGE;
import static by.teachmeskills.springbootproject.ShopConstants.CATEGORIES;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CategoryService categoryService;

    @Override
    public ModelAndView create(User entity) throws AuthorizationException {
        ModelAndView modelAndView = new ModelAndView(PagesPathEnum.REGISTRATION_PAGE.getPath());
        if ((userRepository.findByEmailAndPassword(entity.getEmail(), entity.getPassword())) != null) {
            throw new AuthorizationException("Данный пользователь уже зарегистрирован. Войдите в систему.");
        } else {
            userRepository.create(entity);
            modelAndView.addObject("info", "Пользователь успешно зарегистрирован. Войдите в систему.");
        }
        return modelAndView;
    }

    @Override
    public List<User> read() {
        return userRepository.read();
    }

    @Override
    public User update(User entity) {
        return userRepository.update(entity);
    }

    @Override
    public void delete(User entity) {
        userRepository.delete(entity);
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public ModelAndView authenticate(User user) throws AuthorizationException {
        ModelAndView modelAndView = new ModelAndView();
        ModelMap modelMap = new ModelMap();
        if (Optional.ofNullable(user).isPresent()
                && Optional.ofNullable(user.getEmail()).isPresent()
                && Optional.ofNullable(user.getPassword()).isPresent()) {
            User loggedUser = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
            if (Optional.ofNullable(loggedUser).isPresent()) {
                List<Category> categoriesList = categoryService.read();
                modelMap.addAttribute(CATEGORIES, categoriesList);
                modelAndView.setViewName(HOME_PAGE.getPath());
                modelAndView.addAllObjects(modelMap);
            } else {
                throw new AuthorizationException("Пользователь не зарегистрирован!");
            }
        }
        return modelAndView;
    }
}