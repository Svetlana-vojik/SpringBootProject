package by.teachmeskills.springbootproject.services.impl;

import by.teachmeskills.springbootproject.csv.converters.ProductConverter;
import by.teachmeskills.springbootproject.csv.dto.ProductCsv;
import by.teachmeskills.springbootproject.entities.Category;
import by.teachmeskills.springbootproject.entities.PaginationParams;
import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.entities.SearchParams;
import by.teachmeskills.springbootproject.repositories.ProductRepository;
import by.teachmeskills.springbootproject.repositories.ProductSearchSpecification;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static by.teachmeskills.springbootproject.PagesPathEnum.CATEGORY_PAGE;
import static by.teachmeskills.springbootproject.PagesPathEnum.PRODUCT_PAGE;
import static by.teachmeskills.springbootproject.PagesPathEnum.SEARCH_PAGE;

@Slf4j
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductConverter productConverter;

    @Override
    public Product create(Product entity) {
        return productRepository.save(entity);
    }

    @Override
    public List<Product> read() {
        return productRepository.findAll();
    }

    @Override
    public void delete(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product findById(int id) {
        return productRepository.findById(id);
    }

    @Override
    public ModelAndView getProductsByCategory(int id, PaginationParams params) {
        if (params.getPageNumber() < 0) {
            params.setPageNumber(0);
        }
        ModelMap modelMap = new ModelMap();
        Category category = categoryService.findById(id);
        Pageable pageable = PageRequest.of(params.getPageNumber(), params.getPageSize(), Sort.by("name").ascending());
        category.setProductList(productRepository.findByCategoryId(id, pageable).getContent());
        if (category.getProductList().isEmpty()) {
            params.setPageNumber(params.getPageNumber() - 1);
            pageable = PageRequest.of(params.getPageNumber(), params.getPageSize(), Sort.by("name").ascending());
            category.setProductList(productRepository.findByCategoryId(id, pageable).getContent());
        }
        modelMap.addAttribute("category", category);
        return new ModelAndView(CATEGORY_PAGE.getPath(), modelMap);
    }

    @Override
    public List<Product> findByCategoryId(int id) {
        return productRepository.findByCategoryId(id);
    }

    @Override
    public ModelAndView searchProducts(SearchParams searchParams, PaginationParams paginationParams) {
        if (paginationParams.getPageNumber() < 0) {
            paginationParams.setPageNumber(0);
        }
        ProductSearchSpecification specification = new ProductSearchSpecification(searchParams);
        Pageable pageable = PageRequest.of(paginationParams.getPageNumber(), paginationParams.getPageSize(), Sort.by("name").ascending());
        ModelMap modelMap = new ModelMap();
        List<Product> products = productRepository.findAll(specification, pageable).getContent();
        if (products.isEmpty() && paginationParams.getPageNumber() > 0) {
            paginationParams.setPageNumber(paginationParams.getPageNumber() - 1);
            pageable = PageRequest.of(paginationParams.getPageNumber(), paginationParams.getPageSize(), Sort.by("name").ascending());
            products = productRepository.findAll(specification, pageable).getContent();
        }
        modelMap.addAttribute("categories", categoryService.read());
        modelMap.addAttribute("products", products);
        return new ModelAndView(SEARCH_PAGE.getPath(), modelMap);
    }

    @Override
    public ModelAndView saveProductsFromFile(MultipartFile file, int id) {
        List<ProductCsv> csvProducts = parseCsv(file);
        ModelMap modelMap = new ModelMap();
        List<Product> products = csvProducts.stream().map(productConverter::fromCsv).toList();
        products.stream().forEach(c -> c.setCategory(categoryService.findById(id)));
        productRepository.saveAll(products);
        Pageable pageable = PageRequest.of(0, 0, Sort.by("name").ascending());
        Category category = categoryService.findById(id);
        category.setProductList(productRepository.findByCategoryId(id, pageable).getContent());
        modelMap.addAttribute("category", category);
        return new ModelAndView(CATEGORY_PAGE.getPath(), modelMap);
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
        List<Product> products = productRepository.findByCategoryId(id);
        try (Writer writer = new OutputStreamWriter(servletResponse.getOutputStream())) {
            StatefulBeanToCsv<ProductCsv> beanToCsv = new StatefulBeanToCsvBuilder<ProductCsv>(writer).withSeparator(';').build();
            servletResponse.setContentType("text/csv");
            servletResponse.addHeader("Content-Disposition", "attachment; filename=" + "products.csv");
            beanToCsv.write(products.stream().map(productConverter::toCsv).toList());
        }
    }
}