package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.services.CategoryService;
import by.teachmeskills.springbootproject.services.ProductService;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
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
    private final CategoryService categoryService;
    private final ProductService productService;

    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ModelAndView openCategoryPage(@PathVariable int id) {
        return productService.getProductsByCategory(id);
    }

    @PostMapping("/csv/import/{id}")
    public ModelAndView importCategoriesFromCsv(@RequestParam("file") MultipartFile file, @PathVariable int id) throws IOException {
        return productService.saveProductsFromFile(file, id);
    }

    @PostMapping("/csv/export/{id}")
    public void exportCategoriesToCsv(HttpServletResponse response,  @PathVariable int id) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        productService.saveCategoryProductsToFile(response,id);
    }
}