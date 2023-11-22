package by.teachmeskills.springbootproject.services.impl;

import by.teachmeskills.springbootproject.RequestParamsEnum;
import by.teachmeskills.springbootproject.ShopConstants;
import by.teachmeskills.springbootproject.csv.converters.ProductConverter;
import by.teachmeskills.springbootproject.csv.dto.ProductCsvDto;
import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.entities.Search;
import by.teachmeskills.springbootproject.repositories.ProductRepository;
import by.teachmeskills.springbootproject.repositories.ProductSearchSpecification;
import by.teachmeskills.springbootproject.services.ProductService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
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

import static by.teachmeskills.springbootproject.PagesPathEnum.HOME_PAGE;
import static by.teachmeskills.springbootproject.PagesPathEnum.SEARCH_PAGE;
import static by.teachmeskills.springbootproject.RequestParamsEnum.PRODUCTS;


@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;


    public ProductServiceImpl(ProductRepository productRepository, ProductConverter productConverter) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
    }

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
    public List<Product> getProductsByCategoryId(int categoryId, Pageable pageable) {
        return productRepository.findByCategoryId(categoryId, pageable).getContent();
    }

    @Override
    public ModelAndView searchProducts(Search search, int pageNumber, int pageSize) {
        ModelMap model = new ModelMap();
        if (search != null) {
            if (search.getSearchKey() != null || search.getPriceFrom() != null || search.getPriceTo() != null || search.getCategoryName() != null) {
                if (search.getSearchKey().length() < 3) {
                    model.addAttribute("info", "Не менее трех символов для поиска!");
                }else {
                Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());
                ProductSearchSpecification productSearchSpecification = new ProductSearchSpecification(search);
                List<Product> products = productRepository.findAll(productSearchSpecification, paging).getContent();

                if (!products.isEmpty()) {
                    long totalItems = productRepository.count(productSearchSpecification);
                    int totalPages = (int) (Math.ceil((double) totalItems / pageSize));

                    model.addAttribute(PRODUCTS.getValue(), products);
                    model.addAttribute(RequestParamsEnum.PRODUCTS.getValue(), products);
                    model.addAttribute("totalPages", totalPages);
                    model.addAttribute(RequestParamsEnum.PAGE_NUMBER.getValue(), pageNumber + 1);
                    model.addAttribute(RequestParamsEnum.PAGE_SIZE.getValue(), ShopConstants.PAGE_SIZE);
                } else {
                    model.addAttribute("message", "Ничего не найдено...");
                }}
            }
        }
        model.addAttribute(RequestParamsEnum.SELECTED_PAGE_SIZE.getValue(), pageSize);
        return new ModelAndView(SEARCH_PAGE.getPath(), model);
    }

    @Override
    public ModelAndView saveProductsFromFile(int pageNumber, int pageSize, MultipartFile file) {
        ModelAndView modelAndView = new ModelAndView("redirect:/" + HOME_PAGE.getPath());
        ModelMap model = new ModelMap();
        List<ProductCsvDto> csvProducts = parseCsv(file);
        List<Product> newProducts = Optional.ofNullable(csvProducts)
                .map(list -> list.stream()
                        .map(productConverter::fromCsv)
                        .toList())
                .orElse(null);
        if (Optional.ofNullable(newProducts).isPresent()) {
            newProducts.forEach(productRepository::save);
            int categoryId = newProducts.get(0).getCategory().getId();

            Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());
            List<Product> products = productRepository.findByCategoryId(categoryId, paging).getContent();

            model.addAttribute(PRODUCTS.getValue(), products);
        }
        return modelAndView;
    }

    public List<ProductCsvDto> parseCsv(MultipartFile file) {
        if (Optional.ofNullable(file).isPresent()) {
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                CsvToBean<ProductCsvDto> csvToBean = new CsvToBeanBuilder<ProductCsvDto>(reader).withType(ProductCsvDto.class).
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
            StatefulBeanToCsv<ProductCsvDto> beanToCsv = new StatefulBeanToCsvBuilder<ProductCsvDto>(writer).withSeparator(';').build();
            servletResponse.setContentType("text/csv");
            servletResponse.addHeader("Content-Disposition", "attachment; filename=" + "products.csv");
            beanToCsv.write(products.stream().map(productConverter::toCsv).toList());
        }
    }
}