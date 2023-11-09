package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.entities.PaginationParams;
import by.teachmeskills.springbootproject.services.ProductService;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
@SessionAttributes({"paginationParams"})
public class CategoryController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ModelAndView openCategoryProductsPage(@PathVariable int id, @ModelAttribute("paginationParams") PaginationParams paginationParams) {
        paginationParams.setPageNumber(0);
        paginationParams.setPageSize(2);
        return productService.getProductsByCategory(id, paginationParams);
    }

    @GetMapping("/pagination/{id}/{pageNumber}")
    public ModelAndView getCategoryProductsPaginated(@PathVariable int id, @PathVariable int pageNumber, @SessionAttribute("paginationParams") PaginationParams paginationParams) {
        paginationParams.setPageNumber(pageNumber);
        return productService.getProductsByCategory(id, paginationParams);
    }

    @GetMapping("/changeSize/{id}/{size}")
    public ModelAndView changeCategoryPageSize(@PathVariable int id, @PathVariable int size, @SessionAttribute("paginationParams") PaginationParams paginationParams) {
        paginationParams.setPageSize(size);
        return productService.getProductsByCategory(id, paginationParams);
    }

    @PostMapping("/csv/import/{categoryId}")
    public ModelAndView importCategoriesFromCsv(@RequestParam("file") MultipartFile file, @PathVariable int categoryId) throws IOException {
        return productService.saveProductsFromFile(file, categoryId);
    }

    @PostMapping("/csv/export/{categoryId}")
    public void exportCategoriesToCsv(HttpServletResponse response, @PathVariable int categoryId) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        productService.saveCategoryProductsToFile(response, categoryId);
    }

    @ModelAttribute("paginationParams")
    public PaginationParams setPaginationParams() {
        return new PaginationParams();
    }
}