package by.teachmeskills.springbootproject.services.impl;

import by.teachmeskills.springbootproject.csv.converters.ProductConverter;
import by.teachmeskills.springbootproject.csv.dto.ProductCsv;
import by.teachmeskills.springbootproject.entities.Category;
import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.entities.SearchWord;
import by.teachmeskills.springbootproject.repositories.ProductRepository;
import by.teachmeskills.springbootproject.services.CategoryService;
import by.teachmeskills.springbootproject.services.ProductService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static by.teachmeskills.springbootproject.PagesPathEnum.CATEGORY_PAGE;
import static by.teachmeskills.springbootproject.PagesPathEnum.HOME_PAGE;
import static by.teachmeskills.springbootproject.PagesPathEnum.SEARCH_PAGE;
import static by.teachmeskills.springbootproject.ShopConstants.CATEGORIES;
import static by.teachmeskills.springbootproject.ShopConstants.PRODUCTS;

@Slf4j
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductConverter productConverter;

    @Override
    public Product create(Product entity) {
        return productRepository.create(entity);
    }

    @Override
    public List<Product> read() {
        return productRepository.read();
    }

    @Override
    public Product update(Product entity) {
        return productRepository.update(entity);
    }

    @Override
    public void delete(Product entity) {
        productRepository.delete(entity);
    }

    @Override
    public Product findById(int id) {
        return productRepository.findById(id);
    }

    @Override
    public ModelAndView getProductsByCategory(int id) {
        ModelMap modelMap = new ModelMap();
        Category category = categoryService.findById(id);
        modelMap.addAttribute("category", category);
        return new ModelAndView(CATEGORY_PAGE.getPath(), modelMap);
    }

    @Override
    public List<Product> findByCategoryId(int id) {
        return productRepository.getProductsByCategory(id);
    }

    @Override
    public ModelAndView findProducts(SearchWord searchWord) {
        if (searchWord.getPaginationNumber() < 1) {
            searchWord.setPaginationNumber(1);
        }
        ModelMap modelParam = new ModelMap();
        modelParam.addAttribute(CATEGORIES, categoryService.read());
        if (searchWord.getSearchString() != null) {
            if (searchWord.getSearchString().length() < 3) {
                modelParam.addAttribute("info", "Для поиска введите не менее трех символов");
            } else {
                List<Product> productList = productRepository.findProducts(searchWord);
                if (productList.size() != 0) {
                    modelParam.addAttribute(PRODUCTS, productList);
                } else {
                    modelParam.addAttribute("message", "Ничего не найдено...");
                }
            }
        }
        return new ModelAndView(SEARCH_PAGE.getPath(), modelParam);
    }

    @Override
    public ModelAndView saveProductsFromFile(MultipartFile file, int id) throws IOException {
        ModelAndView modelAndView = new ModelAndView("redirect:/" + HOME_PAGE.getPath());
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<ProductCsv> csvToBean = new CsvToBeanBuilder<ProductCsv>(reader)
                    .withType(ProductCsv.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreQuotations(true)
                    .withSeparator(',')
                    .build();
            List<ProductCsv> productsCsv = new ArrayList<>();
            csvToBean.forEach(productsCsv::add);
            productsCsv.stream().map(productConverter::fromCsv).forEach(productRepository::create);
            return modelAndView;
        }
    }
    @Override
    public List<ProductCsv> parseCsv(MultipartFile file) {
        if (Optional.ofNullable(file).isPresent()) {
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                CsvToBean<ProductCsv> csvToBean = new CsvToBeanBuilder<ProductCsv>(reader).withType(ProductCsv.class).
                        withIgnoreLeadingWhiteSpace(true).withSeparator(';').build();
                return csvToBean.parse();
            } catch (IOException e) {
                log.error("Exception occurred during csv parsing:" + e.getMessage());
            }
        } else {
            log.error("Empty scv file is uploaded");
        }
        return Collections.emptyList();
    }

    @Override
    public void saveCategoryProductsToFile(HttpServletResponse servletResponse, int id) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        List<Product> products = productRepository.getProductsByCategory(id);
        try (Writer writer = new OutputStreamWriter(servletResponse.getOutputStream())) {
            StatefulBeanToCsv<ProductCsv> beanToCsv = new StatefulBeanToCsvBuilder<ProductCsv>(writer).withSeparator(';').build();
            servletResponse.setContentType("text/csv");
            servletResponse.addHeader("Content-Disposition", "attachment; filename=" + "products.csv");
            beanToCsv.write(products.stream().map(productConverter::toCsv).toList());
        }
    }
}