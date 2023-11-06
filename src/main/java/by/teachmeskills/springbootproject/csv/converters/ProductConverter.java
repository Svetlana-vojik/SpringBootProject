package by.teachmeskills.springbootproject.csv.converters;

import by.teachmeskills.springbootproject.csv.dto.ProductCsv;
import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ProductConverter {
    private final CategoryRepository categoryRepository;

    public Product fromCsv(ProductCsv productCsv) {
        return Optional.ofNullable(productCsv).map(p -> Product.builder().id(0).name(p.getName())
                .description(p.getDescription()).imagePath(p.getImagePath())
                .price(p.getPrice()).
                category(categoryRepository.findByName(p.getCategoryName())).build()).orElse(null);
    }

    public ProductCsv toCsv(Product product) {
        return Optional.ofNullable(product).map(p -> ProductCsv.builder().name(p.getName()).
                description(p.getDescription()).imagePath(p.getImagePath())
                .price(p.getPrice()).
                categoryName(p.getCategory().getName()).build()).orElse(null);
    }
}