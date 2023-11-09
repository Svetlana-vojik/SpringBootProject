package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.services.ProductService;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class CategoryController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ModelAndView openCategoryPage(@PathVariable int id) {
        return productService.getProductsByCategory(id);
    }

    @PostMapping("/csv/import/{categoryId}")
    public ModelAndView importCategoriesFromCsv(@RequestParam("file") MultipartFile file, @PathVariable int categoryId) throws IOException {
        return productService.saveProductsFromFile(file, categoryId);
    }

    @PostMapping("/csv/export/{categoryId}")
    public void exportCategoriesToCsv(HttpServletResponse response, @PathVariable int categoryId) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        productService.saveCategoryProductsToFile(response, categoryId);
    }
}