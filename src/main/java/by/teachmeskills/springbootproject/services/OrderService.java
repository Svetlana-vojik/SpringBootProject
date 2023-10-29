package by.teachmeskills.springbootproject.services;

import by.teachmeskills.springbootproject.entities.Cart;
import by.teachmeskills.springbootproject.entities.Order;
import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.AuthorizationException;
import by.teachmeskills.springbootproject.exceptions.CartIsEmptyException;
import by.teachmeskills.springbootproject.repositories.OrderRepository;
import by.teachmeskills.springbootproject.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static by.teachmeskills.springbootproject.PagesPathEnum.CART_PAGE;
import static by.teachmeskills.springbootproject.PagesPathEnum.USER_PROFILE_PAGE;
import static by.teachmeskills.springbootproject.ShopConstants.ORDERS;
import static by.teachmeskills.springbootproject.ShopConstants.USER;

@Service
@AllArgsConstructor
public class OrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public ModelAndView create(User user, Cart cart) throws CartIsEmptyException, AuthorizationException {
        if (Optional.ofNullable(user).isEmpty()) {
            throw new AuthorizationException("Пользователь не авторизован!");
        }
        if (cart.getTotalPrice() == 0) {
            throw new CartIsEmptyException("Корзина пуста!");
        }
        Order order = Order.builder().orderDate(LocalDate.now()).price(cart.getTotalPrice())
                .user(user).productList(cart.getProducts()).build();
        orderRepository.create(order);
        cart.clear();
        cart.setTotalPrice(0);
        ModelAndView modelAndView = new ModelAndView(CART_PAGE.getPath());
        modelAndView.addObject("info", "Заказ оформлен.");
        return modelAndView;
    }

    public ModelAndView findUserOrders(User user) throws AuthorizationException {
        ModelAndView modelAndView = new ModelAndView();
        ModelMap modelMap = new ModelMap();
        if (Optional.ofNullable(user).isPresent()
                && Optional.ofNullable(user.getEmail()).isPresent()
                && Optional.ofNullable(user.getPassword()).isPresent()) {
            User loggedUser = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
            if (Optional.ofNullable(loggedUser).isPresent()) {
                List<Order> orders = orderRepository.findByUser(loggedUser);
                modelMap.addAttribute(USER, loggedUser);
                modelMap.addAttribute(ORDERS, orders);
                modelAndView.addAllObjects(modelMap);
            }
        } else {
            throw new AuthorizationException("Пользователь не авторизован!");
        }
        modelAndView.setViewName(USER_PROFILE_PAGE.getPath());
        return modelAndView;
    }

}