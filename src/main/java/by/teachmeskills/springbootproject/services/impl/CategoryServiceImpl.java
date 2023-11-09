package by.teachmeskills.springbootproject.services.impl;

import by.teachmeskills.springbootproject.csv.converters.CategoryConverter;
import by.teachmeskills.springbootproject.csv.dto.CategoryCsvDto;
import by.teachmeskills.springbootproject.entities.Category;
import by.teachmeskills.springbootproject.entities.PaginationParams;
import by.teachmeskills.springbootproject.repositories.CategoryRepository;
import by.teachmeskills.springbootproject.services.CategoryService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.persistence.EntityNotFoundException;
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

import static by.teachmeskills.springbootproject.PagesPathEnum.HOME_PAGE;
import static by.teachmeskills.springbootproject.ShopConstants.CATEGORIES;

@Service
@Slf4j
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;

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
    public ModelAndView importCategoriesFromCsv(MultipartFile file) throws EntityNotFoundException {
        List<CategoryCsvDto> csvCategories = parseCsv(file);
        ModelMap modelMap = new ModelMap();
        List<Category> newCategories = Optional.ofNullable(csvCategories)
                .map(list -> list.stream()
                        .map(categoryConverter::fromCsv)
                        .toList())
                .orElse(null);

        if (Optional.ofNullable(newCategories).isPresent()) {
            newCategories.forEach(categoryRepository::save);
        }
        List<Category> categories = categoryRepository.findAll();
        modelMap.addAttribute(CATEGORIES, categories);
        return new ModelAndView(HOME_PAGE.getPath(), modelMap);
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

    @Override
    public ModelAndView getAllCategories(PaginationParams paginationParams) {
        ModelMap modelMap = new ModelMap();
        Pageable pageable = PageRequest.of(paginationParams.getPageNumber(), paginationParams.getPageSize(), Sort.by("name").ascending());
        List<Category> categories = categoryRepository.findAll(pageable).getContent();
        if (paginationParams.getPageNumber() < 0) {
            paginationParams.setPageNumber(0);
        }
        if (categories.isEmpty()) {
            throw new EntityNotFoundException("Категории не найдены.");
        }

        modelMap.addAttribute(CATEGORIES, categories);
        return new ModelAndView(HOME_PAGE.getPath(), modelMap);
    }
}