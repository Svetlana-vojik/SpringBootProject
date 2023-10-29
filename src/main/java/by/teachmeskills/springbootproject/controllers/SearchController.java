package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.entities.SearchWord;
import by.teachmeskills.springbootproject.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import static by.teachmeskills.springbootproject.ShopConstants.SEARCH_WORD;

@RestController
@RequestMapping("/search")
@AllArgsConstructor
@SessionAttributes(SEARCH_WORD)
public class SearchController {

    private final ProductService productService;

    @GetMapping
    public ModelAndView openSearchPage(@ModelAttribute(SEARCH_WORD) SearchWord searchWord) {
        return productService.findProductsByWord(searchWord);
    }

    @PostMapping
    public ModelAndView search(@RequestParam String searchString, @ModelAttribute(SEARCH_WORD) SearchWord searchWord) {
        searchWord.setSearchString(searchString);
        return productService.findProductsByWord(searchWord);
    }

    @GetMapping("{pageNumber}")
    public ModelAndView openPageNumber(@PathVariable int pageNumber, @ModelAttribute(SEARCH_WORD) SearchWord searchWord) {
        searchWord.setPaginationNumber(pageNumber);
        return productService.findProductsByWord(searchWord);
    }

    @ModelAttribute(SEARCH_WORD)
    public SearchWord initSearchWord() {
        return new SearchWord();
    }
}