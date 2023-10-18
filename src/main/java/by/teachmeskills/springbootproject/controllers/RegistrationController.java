package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.entities.User;

import by.teachmeskills.springbootproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import static by.teachmeskills.springbootproject.ShopConstants.USER;
import static by.teachmeskills.springbootproject.PagesPathEnum.REGISTRATION_PAGE;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView openRegisterPage() {
        return new ModelAndView(REGISTRATION_PAGE.getPath());
    }

    @PostMapping
    public ModelAndView register(@ModelAttribute(USER) User user){
        return userService.createUser(user);
    }

    @ModelAttribute(USER)
    public User setUpUser() {
        return new User();
    }
}