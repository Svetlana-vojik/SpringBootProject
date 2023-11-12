package by.teachmeskills.springbootproject.services.impl;

import by.teachmeskills.springbootproject.RequestParamsEnum;
import by.teachmeskills.springbootproject.ShopConstants;
import by.teachmeskills.springbootproject.csv.converters.CategoryConverter;
import by.teachmeskills.springbootproject.csv.dto.CategoryCsvDto;
import by.teachmeskills.springbootproject.entities.Category;
import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.repositories.CategoryRepository;
import by.teachmeskills.springbootproject.repositories.ProductRepository;
import by.teachmeskills.springbootproject.services.CategoryService;
import by.teachmeskills.springbootproject.services.ProductService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.persistence.EntityNotFoundException;
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

import static by.teachmeskills.springbootproject.PagesPathEnum.CATEGORY_PAGE;
import static by.teachmeskills.springbootproject.PagesPathEnum.HOME_PAGE;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;
    private final ProductService productService;
    private final ProductRepository productRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryConverter categoryConverter, ProductService productService, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryConverter = categoryConverter;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @Override
    public Category create(Category entity) {
        return categoryRepository.save(entity);
    }

    @Override
    public List<Category> read() {
        return categoryRepository.findAll();
    }

    public void delete(int id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category findById(int id) {
        return categoryRepository.findNameById(id);
    }

    @Override
    public ModelAndView getAllCategories(int pageNumber, int pageSize) throws EntityNotFoundException {
        ModelMap model = new ModelMap();
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());
        List<Category> categories = categoryRepository.findAll(paging).getContent();
        if (categories.isEmpty()) {
            throw new EntityNotFoundException("Категории не найдены. Попробуйте позже.");
        }
        long totalItems = categoryRepository.count();
        int totalPages = (int) (Math.ceil((double) totalItems / pageSize));

        model.addAttribute(RequestParamsEnum.PAGE_NUMBER.getValue(), pageNumber + 1);
        model.addAttribute(RequestParamsEnum.PAGE_SIZE.getValue(), ShopConstants.PAGE_SIZE);
        model.addAttribute(RequestParamsEnum.SELECTED_PAGE_SIZE.getValue(), pageSize);
        model.addAttribute(RequestParamsEnum.TOTAL_PAGES.getValue(), totalPages);
        model.addAttribute(RequestParamsEnum.CATEGORIES.getValue(), categories);
        return new ModelAndView(HOME_PAGE.getPath(), model);
    }

    @Override
    public ModelAndView getCategoryById(int id, int pageNumber, int pageSize) throws EntityNotFoundException {
        ModelMap model = new ModelMap();

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Категории с id %d не найдено.", id)));

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());
        List<Product> products = productService.getProductsByCategoryId(category.getId(), pageable);
        if (products.isEmpty()) {
            throw new EntityNotFoundException("Продукты не найдены.");
        }
        int totalItems = productRepository.findByCategoryId(id).size();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        model.addAttribute(RequestParamsEnum.PRODUCTS.getValue(), products);
        model.addAttribute(RequestParamsEnum.CATEGORY_ID.getValue(), id);
        model.addAttribute(RequestParamsEnum.PAGE_NUMBER.getValue(), pageNumber + 1);
        model.addAttribute(RequestParamsEnum.PAGE_SIZE.getValue(), ShopConstants.PAGE_SIZE);
        model.addAttribute(RequestParamsEnum.SELECTED_PAGE_SIZE.getValue(), pageSize);
        model.addAttribute(RequestParamsEnum.TOTAL_PAGES.getValue(), totalPages);
        return new ModelAndView(CATEGORY_PAGE.getPath(), model);
    }

    @Override
    public ModelAndView importCategoriesFromCsv(int pageNumber, int pageSize, MultipartFile file) throws EntityNotFoundException {
        List<CategoryCsvDto> csvCategories = parseCsv(file);
        List<Category> newCategories = Optional.ofNullable(csvCategories)
                .map(list -> list.stream()
                        .map(categoryConverter::fromCsv)
                        .toList())
                .orElse(null);
        if (Optional.ofNullable(newCategories).isPresent()) {
            newCategories.forEach(categoryRepository::save);
        }
        return getAllCategories(pageNumber, pageSize);
    }

    public List<CategoryCsvDto> parseCsv(MultipartFile file) {
        if (Optional.ofNullable(file).isPresent()) {
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                CsvToBean<CategoryCsvDto> csvToBean = new CsvToBeanBuilder<CategoryCsvDto>(reader).withType(CategoryCsvDto.class).
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
    public void exportCategoriesToCsv(HttpServletResponse response) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        List<Category> categories = categoryRepository.findAll();
        try (Writer writer = new OutputStreamWriter(response.getOutputStream())) {
            StatefulBeanToCsv<Category> statefulBeanToCsv = new StatefulBeanToCsvBuilder<Category>(writer).withSeparator(';').build();
            response.setContentType("text/csv");
            response.addHeader("Content-Disposition", "attachment; filename=" + "categories.csv");
            statefulBeanToCsv.write(categories);
        }
    }
}