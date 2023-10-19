package by.teachmeskills.springbootproject.utils.actuator;

import by.teachmeskills.springbootproject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.ModelAndView;

import static by.teachmeskills.springbootproject.PagesPathEnum.STATISTIC_PAGE;
import static by.teachmeskills.springbootproject.ShopConstants.TIME;

@Component
@Endpoint(id = "statistic")
public class ShopStatisticEndpoint {
    private final ProductService productService;

    @Autowired
    public ShopStatisticEndpoint(ProductService productService) {
        this.productService = productService;
    }

    @ReadOperation
    public ModelAndView getProductsStatistic() {
        ModelAndView modelAndView = new ModelAndView();
        ModelMap modelMap = new ModelMap();

        StopWatch stopWatch = new StopWatch("App");
        stopWatch.start("App Startup");
        productService.read();
        stopWatch.stop();

        modelMap.addAttribute(TIME, stopWatch.getTotalTimeSeconds());
        modelAndView.setViewName(STATISTIC_PAGE.getPath());
        modelAndView.addAllObjects(modelMap);
        return modelAndView;
    }
}