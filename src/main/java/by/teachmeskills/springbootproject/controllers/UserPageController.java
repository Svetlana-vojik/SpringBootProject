package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.AuthorizationException;
import by.teachmeskills.springbootproject.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import static by.teachmeskills.springbootproject.ShopConstants.USER;

@RestController
@RequestMapping("/userPage")
@SessionAttributes(USER)
@AllArgsConstructor
public class UserPageController {
    private final OrderService orderService;

    @GetMapping
    public ModelAndView openAccountPage(@SessionAttribute(name = USER, required = false) User user) throws AuthorizationException {
        return orderService.findUserOrders(user);
    }
}