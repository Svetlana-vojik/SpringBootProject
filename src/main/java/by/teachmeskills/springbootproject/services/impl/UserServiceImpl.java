package by.teachmeskills.springbootproject.services.impl;

import by.teachmeskills.springbootproject.PagesPathEnum;
import by.teachmeskills.springbootproject.RequestParamsEnum;
import by.teachmeskills.springbootproject.ShopConstants;
import by.teachmeskills.springbootproject.entities.Category;
import by.teachmeskills.springbootproject.entities.Order;
import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.AuthorizationException;
import by.teachmeskills.springbootproject.repositories.CategoryRepository;
import by.teachmeskills.springbootproject.repositories.OrderRepository;
import by.teachmeskills.springbootproject.repositories.UserRepository;
import by.teachmeskills.springbootproject.services.CategoryService;
import by.teachmeskills.springbootproject.services.OrderService;
import by.teachmeskills.springbootproject.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

import static by.teachmeskills.springbootproject.PagesPathEnum.USER_PROFILE_PAGE;
import static by.teachmeskills.springbootproject.ShopConstants.ORDERS;
import static by.teachmeskills.springbootproject.ShopConstants.USER;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    public UserServiceImpl(UserRepository userRepository, CategoryService categoryService, PasswordEncoder passwordEncoder, CategoryRepository categoryRepository, OrderService orderService, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.categoryService = categoryService;
        this.passwordEncoder = passwordEncoder;
        this.categoryRepository = categoryRepository;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }


    @Override
    public ModelAndView createUser(User entity) throws AuthorizationException {
        ModelAndView modelAndView = new ModelAndView(PagesPathEnum.REGISTRATION_PAGE.getPath());
        if ((userRepository.findByEmailAndPassword(entity.getEmail(), entity.getPassword())) != null) {
            throw new AuthorizationException("Данный пользователь уже зарегистрирован. Войдите в систему.");
        } else {
            userRepository.save(entity);
            modelAndView.addObject("info", "Пользователь успешно зарегистрирован. Войдите в систему.");
        }
        return modelAndView;
    }

    @Override
    public User create(User entity) {
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return userRepository.save(entity);
    }

    @Override
    public List<User> read() {
        return userRepository.findAll();
    }


    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public ModelAndView authenticate(User user) throws AuthorizationException {
        if (Optional.ofNullable(user).isPresent()
                && Optional.ofNullable(user.getEmail()).isPresent()
                && Optional.ofNullable(user.getPassword()).isPresent()) {
            User loggedUser = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
            if (Optional.ofNullable(loggedUser).isPresent()) {
                ModelMap model = new ModelMap();

                Pageable paging = PageRequest.of(0, ShopConstants.PAGE_SIZE, Sort.by("name").ascending());
                List<Category> categories = categoryRepository.findAll(paging).getContent();
                long totalItems = categoryRepository.count();
                int totalPages = (int) (Math.ceil((double) totalItems / ShopConstants.PAGE_SIZE));

                model.addAttribute(RequestParamsEnum.PAGE_NUMBER.getValue(), 1);
                model.addAttribute(RequestParamsEnum.PAGE_SIZE.getValue(), ShopConstants.PAGE_SIZE);
                model.addAttribute(RequestParamsEnum.SELECTED_PAGE_SIZE.getValue(), ShopConstants.PAGE_SIZE);
                model.addAttribute(RequestParamsEnum.TOTAL_PAGES.getValue(), totalPages);
                model.addAttribute(RequestParamsEnum.CATEGORIES.getValue(), categories);
                model.addAttribute(RequestParamsEnum.USER.getValue(), loggedUser);
                return new ModelAndView(PagesPathEnum.HOME_PAGE.getPath(), model);
            } else {
                throw new AuthorizationException("Пользователь не зарегистрирован!");
            }
        }
        throw new AuthorizationException("Пользователь не найден");
    }

    @Override
    public ModelAndView generateAccountPage(User user) {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(USER, user);
        if (orderRepository.findByUserId(user.getId()).isEmpty()) {
            return new ModelAndView(USER_PROFILE_PAGE.getPath(), modelMap);
        }
        List<Order> orders = orderRepository.findByUserId(user.getId());
        modelMap.addAttribute(ORDERS, orders);
        return new ModelAndView(USER_PROFILE_PAGE.getPath(), modelMap);
    }

    @Override
    public User getCurrentUser() {
        User user = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            user = userRepository.findByEmail(username);
        }
        return user;
    }
}