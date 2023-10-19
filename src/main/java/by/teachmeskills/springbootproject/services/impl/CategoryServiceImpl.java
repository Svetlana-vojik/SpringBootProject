package by.teachmeskills.springbootproject.services.impl;

import by.teachmeskills.springbootproject.ShopConstants;
import by.teachmeskills.springbootproject.entities.Category;
import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.PagesPathEnum;
import by.teachmeskills.springbootproject.repositories.CategoryRepository;
import by.teachmeskills.springbootproject.repositories.ProductRepository;
import by.teachmeskills.springbootproject.services.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

import static by.teachmeskills.springbootproject.ShopConstants.CATEGORY;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productService) {
        this.categoryRepository = categoryRepository;
        this.productService = productService;
    }

    @Override
    public ModelAndView create(Category entity) {
        return new ModelAndView();
    }

    @Override
    public List<Category> read() {
        return categoryRepository.read();
    }

    @Override
    public Category update(Category entity) {
        return categoryRepository.update(entity);
    }

    @Override
    public void delete(int id) {
        categoryRepository.delete(id);
    }

    @Override
    public ModelAndView findNameById(int id) {
        ModelAndView modelAndView = new ModelAndView(PagesPathEnum.CATEGORY_PAGE.getPath());
        Category category = categoryRepository.findNameById(id);
        if (Optional.ofNullable(category).isPresent()) {
            List<Product> products = productService.findByCategoryId(id);
            if (products.size() != 0) {
                modelAndView.addObject(ShopConstants.PRODUCTS, products);
            } else {
                modelAndView.addObject("message", "В данной категории пока нет товаров...");
            }
            modelAndView.addObject(CATEGORY, category.getName());
        }
        return modelAndView;
    }
}