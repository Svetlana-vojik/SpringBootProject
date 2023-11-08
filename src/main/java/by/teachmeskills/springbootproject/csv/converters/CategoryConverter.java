package by.teachmeskills.springbootproject.csv.converters;

import by.teachmeskills.springbootproject.csv.dto.CategoryCsv;
import by.teachmeskills.springbootproject.entities.Category;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class CategoryConverter {
     public CategoryCsv toCsv(Category category) {
        return Optional.ofNullable(category).map(c -> CategoryCsv.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .imagePath(c.getImagePath())
                        .rating(c.getRating())
                        .build())
                .orElse(null);
    }

    public Category fromCsv(CategoryCsv CategoryCsv) {
        return Optional.ofNullable(CategoryCsv).map(cd -> Category.builder()
                        .name(cd.getName())
                        .imagePath(cd.getImagePath())
                        .rating(cd.getRating())
                        .build())
                .orElse(null);
    }
}