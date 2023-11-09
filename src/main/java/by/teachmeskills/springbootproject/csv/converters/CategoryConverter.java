package by.teachmeskills.springbootproject.csv.converters;

import by.teachmeskills.springbootproject.csv.dto.CategoryCsvDto;
import by.teachmeskills.springbootproject.entities.Category;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class CategoryConverter {
    private final ProductConverter productConverter;

    public CategoryCsvDto toDto(Category category) {
        return Optional.ofNullable(category).map(c -> CategoryCsvDto.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .rating(c.getRating())
                        .products(Optional.ofNullable(c.getProductList()).map(products -> products.stream().map(productConverter::toCsv).toList()).orElse(List.of()))
                        .build())
                .orElse(null);
    }

    public Category fromDto(CategoryCsvDto CategoryCsv) {
        return Optional.ofNullable(CategoryCsv).map(cd -> Category.builder()
                        .name(cd.getName())
                        .rating(cd.getRating())
                        .build())
                .orElse(null);
    }
}