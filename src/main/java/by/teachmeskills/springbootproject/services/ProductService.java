package by.teachmeskills.springbootproject.services;

import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.entities.Search;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

public interface ProductService extends BaseService<Product> {
    Product findById(int id);

    List<Product> getProductsByCategoryId(int categoryId, Pageable pageable);

    ModelAndView searchProducts(Search search, int pageNumber, int pageSize);

    ModelAndView saveProductsFromFile(int pageNumber, int pageSize, MultipartFile file);

    void saveCategoryProductsToFile(HttpServletResponse servletResponse, int id) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;
}