package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.ShopConstants;
import by.teachmeskills.springbootproject.services.CategoryService;
import by.teachmeskills.springbootproject.services.ProductService;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public CategoryController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/{categoryId}")
    public ModelAndView openCategoryPage(@PathVariable Integer categoryId,
                                         @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                         @RequestParam(required = false, defaultValue = "" + ShopConstants.PAGE_SIZE) Integer pageSize) throws EntityNotFoundException {
 return categoryService.getCategoryById(categoryId, pageNumber, pageSize);
    }

    @PostMapping("/csv/import")
    public ModelAndView  importCategoriesFromCsv(@RequestParam("file") MultipartFile file,
                                                 @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                                 @RequestParam(required = false, defaultValue = "" + ShopConstants.PAGE_SIZE) Integer pageSize) {
        return productService.saveProductsFromFile(pageNumber, pageSize, file);
    }

    @PostMapping("/csv/export/{categoryId}")
    public void exportCategoriesToCsv(HttpServletResponse response, @PathVariable int categoryId) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        productService.saveCategoryProductsToFile(response, categoryId);
    }
}