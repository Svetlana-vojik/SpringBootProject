package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import static by.teachmeskills.springbootproject.PagesPathEnum.STATISTIC_PAGE;
import static by.teachmeskills.springbootproject.ShopConstants.TIME;


@Component
@RestControllerEndpoint(id = "statistics")
@AllArgsConstructor
public class StatisticController {

    private final UserService userService;

    @GetMapping("/users")
    public ModelAndView getUsersStatistic() {
        ModelAndView modelAndView = new ModelAndView(STATISTIC_PAGE.getPath());
        StopWatch stopWatch = new StopWatch("App");
        stopWatch.start("App Startup");
        userService.read();
        stopWatch.stop();
        return modelAndView.addObject(TIME, stopWatch.getTotalTimeSeconds());
    }
}