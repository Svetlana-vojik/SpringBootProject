package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.ShopConstants;
import by.teachmeskills.springbootproject.services.CategoryService;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
@RequestMapping("/home")
public class HomeController {
    private final CategoryService categoryService;

    public HomeController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ModelAndView openHomePage(@RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                     @RequestParam(required = false, defaultValue = "" + ShopConstants.PAGE_SIZE) Integer pageSize) throws EntityNotFoundException {
        return categoryService.getAllCategories(pageNumber, pageSize);
    }

    @PostMapping("/csv/import")
    public ModelAndView importCategoriesFromCsv(@RequestParam("file") MultipartFile file,
                                                @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                                @RequestParam(required = false, defaultValue = "" + ShopConstants.PAGE_SIZE) Integer pageSize) throws EntityNotFoundException {
        return categoryService.importCategoriesFromCsv(pageNumber, pageSize, file);
    }

    @PostMapping("/csv/export")
    public void exportCategoriesToCsv(HttpServletResponse response) throws
            CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        categoryService.exportCategoriesToCsv(response);
    }
}