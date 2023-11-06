package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.services.CategoryService;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

import static by.teachmeskills.springbootproject.PagesPathEnum.HOME_PAGE;
import static by.teachmeskills.springbootproject.ShopConstants.CATEGORIES;

@RestController
@RequestMapping("/home")
public class HomeController {
    private final CategoryService categoryService;

    public HomeController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ModelAndView openHomePage() {
        ModelAndView modelAndView = new ModelAndView(HOME_PAGE.getPath());
        return modelAndView.addObject(CATEGORIES, categoryService.read());
    }
    @PostMapping("/csv/import")
    public ModelAndView importCategoriesFromCsv(@RequestParam("file") MultipartFile file) {
        return categoryService.importCategoriesFromCsv(file);
    }

    @PostMapping("/csv/export")
    public void exportCategoriesToCsv(HttpServletResponse response) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        categoryService.exportCategoriesToCsv(response);
    }
}