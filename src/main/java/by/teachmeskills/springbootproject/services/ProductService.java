package by.teachmeskills.springbootproject.services;

import by.teachmeskills.springbootproject.csv.dto.ProductCsv;
import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.entities.SearchWord;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
public interface ProductService extends BaseService<Product> {
    Product findById(int id);
    ModelAndView getProductsByCategory(int id);
    List<Product> findByCategoryId(int id);

    ModelAndView findProducts(SearchWord searchWord);

    ModelAndView saveProductsFromFile(MultipartFile file, int id) throws IOException;

    List<ProductCsv> parseCsv(MultipartFile file);

    void saveCategoryProductsToFile(HttpServletResponse servletResponse, int id) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;
}