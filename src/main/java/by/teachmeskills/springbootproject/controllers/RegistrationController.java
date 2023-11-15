package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.AuthorizationException;
import by.teachmeskills.springbootproject.services.UserService;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import static by.teachmeskills.springbootproject.ShopConstants.ADDRESS;
import static by.teachmeskills.springbootproject.ShopConstants.BIRTHDAY;
import static by.teachmeskills.springbootproject.ShopConstants.EMAIL;
import static by.teachmeskills.springbootproject.ShopConstants.NAME;
import static by.teachmeskills.springbootproject.ShopConstants.PASSWORD;
import static by.teachmeskills.springbootproject.ShopConstants.SURNAME;
import static by.teachmeskills.springbootproject.ShopConstants.USER;
import static by.teachmeskills.springbootproject.PagesPathEnum.REGISTRATION_PAGE;
import static by.teachmeskills.springbootproject.utils.ErrorUtil.populateError;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView openRegisterPage() {
        return new ModelAndView(REGISTRATION_PAGE.getPath());
    }

    @PostMapping
    public ModelAndView register(@Valid @ModelAttribute(USER) User user, BindingResult bindingResult, ModelAndView modelAndView){
        if (bindingResult.hasErrors()) {
            populateError(EMAIL, modelAndView, bindingResult);
            populateError(PASSWORD, modelAndView, bindingResult);
            populateError(NAME, modelAndView, bindingResult);
            populateError(SURNAME, modelAndView, bindingResult);
            populateError(BIRTHDAY, modelAndView, bindingResult);
            populateError(ADDRESS, modelAndView, bindingResult);
            modelAndView.setViewName(REGISTRATION_PAGE.getPath());
            return modelAndView;
        }
        return userService.createUser(user);
    }

    @ModelAttribute(USER)
    public User setUpUser() {
        return new User();
    }
}