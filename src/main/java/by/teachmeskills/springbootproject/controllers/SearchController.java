package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.entities.PaginationParams;
import by.teachmeskills.springbootproject.entities.SearchParams;
import by.teachmeskills.springbootproject.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

import static by.teachmeskills.springbootproject.PagesPathEnum.SEARCH_PAGE;

@RestController
@RequestMapping("/search")
@AllArgsConstructor
@SessionAttributes({"searchParams", "paginationParams"})
public class SearchController {

    private final ProductService productService;

    @GetMapping
    public ModelAndView openSearchPage() {
        return new ModelAndView(SEARCH_PAGE.getPath());
    }

    @PostMapping
    public ModelAndView searchByNameOrDescription(@RequestParam("searchKey") String searchKey, @ModelAttribute("searchParams") SearchParams searchParams,
                                                  @ModelAttribute("paginationParams") PaginationParams paginationParams) {
        paginationParams.setPageNumber(0);
        searchParams.setSearchKey(searchKey);
        searchParams.setCategoryName(null);
        searchParams.setPriceTo(null);
        searchParams.setPriceFrom(null);
        return productService.searchProducts(searchParams, paginationParams);
    }

    @GetMapping("/pagination/{pageNumber}")
    public ModelAndView searchPaginated(@PathVariable int pageNumber, @SessionAttribute("searchParams") SearchParams searchParams,
                                        @SessionAttribute("paginationParams") PaginationParams paginationParams) {
        paginationParams.setPageNumber(pageNumber);
        return productService.searchProducts(searchParams, paginationParams);
    }

    @PostMapping("/applyFilter")
    public ModelAndView applyFilter(@RequestParam(value = "searchKey", required = false) String searchKey,
                                    @RequestParam(value = "categoryName", required = false) String categoryName,
                                    @RequestParam(value = "priceFrom", required = false) Integer priceFrom,
                                    @RequestParam(value = "priceTo", required = false) Integer priceTo,
                                    @SessionAttribute("searchParams") SearchParams searchParams,
                                    @SessionAttribute("paginationParams") PaginationParams paginationParams) {
        paginationParams.setPageNumber(0);
        if (Optional.ofNullable(categoryName).isPresent()) {
            searchParams.setCategoryName(categoryName);
        }
        searchParams.setPriceFrom(priceFrom);
        searchParams.setPriceTo(priceTo);
        searchParams.setSearchKey(searchKey);
        return productService.searchProducts(searchParams, paginationParams);
    }

    @GetMapping("/setPageSize/{pageSize}")
    public ModelAndView changePageSize(@PathVariable int pageSize, @SessionAttribute("searchParams") SearchParams searchParams,
                                       @SessionAttribute("paginationParams") PaginationParams paginationParams) {
        paginationParams.setPageSize(pageSize);
        return productService.searchProducts(searchParams, paginationParams);
    }

    @ModelAttribute("searchParams")
    public SearchParams setSearchParams() {
        return new SearchParams();
    }

    @ModelAttribute("paginationParams")
    public PaginationParams setPaginationParams() {
        return new PaginationParams();
    }
}