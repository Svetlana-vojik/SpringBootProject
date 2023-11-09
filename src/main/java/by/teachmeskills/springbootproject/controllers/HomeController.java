package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.entities.PaginationParams;
import by.teachmeskills.springbootproject.services.CategoryService;
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
@RequestMapping("/home")
@AllArgsConstructor
@SessionAttributes({"paginationParams"})
public class HomeController {
    private final CategoryService categoryService;

    @GetMapping
    public ModelAndView openHomePage(@ModelAttribute("paginationParams") PaginationParams paginationParams) {
        paginationParams.setPageNumber(0);
        paginationParams.setPageSize(5);
        return categoryService.getAllCategories(paginationParams);
    }

    @GetMapping("/pagination/{page}")
    public ModelAndView homePagePaginated(@PathVariable int page,
                                          @SessionAttribute(required=false,name="paginationParams") PaginationParams paginationParams) {
        paginationParams.setPageNumber(page);
        return categoryService.getAllCategories(paginationParams);
    }

    @GetMapping("/changeSize/{size}")
    public ModelAndView changePage(@PathVariable int size,
                                   @SessionAttribute(required=false,name="paginationParams") PaginationParams paginationParams) {
        paginationParams.setPageSize(size);
        return categoryService.getAllCategories(paginationParams);
    }

    @ModelAttribute("paginationParams")
    public PaginationParams setPaginationParams() {
        return new PaginationParams();
    }

    @PostMapping("/csv/import")
    public ModelAndView importCategoriesFromCsv(@RequestParam("file") MultipartFile file) {
        return categoryService.importCategoriesFromCsv(file);
    }

    @PostMapping("/csv/export")
    public void exportCategoriesToCsv(HttpServletResponse response) throws
            CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        categoryService.exportCategoriesToCsv(response);
    }
}