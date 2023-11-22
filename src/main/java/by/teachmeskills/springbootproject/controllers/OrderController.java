package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.entities.Cart;
import by.teachmeskills.springbootproject.exceptions.AuthorizationException;
import by.teachmeskills.springbootproject.exceptions.CartIsEmptyException;
import by.teachmeskills.springbootproject.services.OrderService;
import by.teachmeskills.springbootproject.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import static by.teachmeskills.springbootproject.ShopConstants.CART;

@RestController
@RequestMapping("/cart")
@SessionAttributes({CART})
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/order")
    public ModelAndView makeOrder(@ModelAttribute(CART) Cart cart) throws CartIsEmptyException, AuthorizationException {
        return orderService.create(userService.getCurrentUser(), cart);
    }
}