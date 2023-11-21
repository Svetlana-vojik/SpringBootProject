package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.ShopConstants;
import by.teachmeskills.springbootproject.entities.SearchParams;
import by.teachmeskills.springbootproject.services.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import static by.teachmeskills.springbootproject.ShopConstants.SEARCH_WORD;

@RestController
@RequestMapping("/search")
@SessionAttributes({SEARCH_WORD})
public class SearchController {

    private final ProductService productService;

    public SearchController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ModelAndView openSearchPage(@SessionAttribute(name = SEARCH_WORD, required = false) SearchParams search,
                                       @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                       @RequestParam(required = false, defaultValue = "" + ShopConstants.PAGE_SIZE) Integer pageSize) {
        return productService.searchProducts(search, pageNumber, pageSize);
    }

    @PostMapping
    public ModelAndView search(@ModelAttribute(SEARCH_WORD) SearchParams search,
                               @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                               @RequestParam(required = false, defaultValue = "" + ShopConstants.PAGE_SIZE) Integer pageSize) {
        return productService.searchProducts(search, pageNumber, pageSize);
    }

    @ModelAttribute(SEARCH_WORD)
    public SearchParams setSearchParams() {
        return new SearchParams();
    }

}