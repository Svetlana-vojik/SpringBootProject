package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.services.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import static by.teachmeskills.springbootproject.PagesPathEnum.PRODUCT_PAGE;
import static by.teachmeskills.springbootproject.ShopConstants.PRODUCT;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ModelAndView openProductPage(@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView(PRODUCT_PAGE.getPath());
        modelAndView.addObject(PRODUCT, productService.findById(id));
        return modelAndView;
    }
}