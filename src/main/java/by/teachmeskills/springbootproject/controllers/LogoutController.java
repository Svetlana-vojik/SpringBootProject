package by.teachmeskills.springbootproject.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import static by.teachmeskills.springbootproject.PagesPathEnum.HOME_PAGE;

@RestController
@RequestMapping("/logout")
public class LogoutController {
    @GetMapping
    public ModelAndView logOut() {
        return new ModelAndView(HOME_PAGE.getPath());
    }
}