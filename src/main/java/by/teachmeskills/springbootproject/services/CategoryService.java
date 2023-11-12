package by.teachmeskills.springbootproject.services;

import by.teachmeskills.springbootproject.entities.Category;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

public interface CategoryService extends BaseService<Category> {
    Category findById(int id);

    ModelAndView importCategoriesFromCsv(int pageNumber, int pageSize, MultipartFile file) throws EntityNotFoundException;

    void exportCategoriesToCsv(HttpServletResponse response) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;

    ModelAndView getAllCategories(int pageNumber, int pageSize) throws EntityNotFoundException;

    ModelAndView getCategoryById(int id, int pageNumber, int pageSize) throws EntityNotFoundException;
}