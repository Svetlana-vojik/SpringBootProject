package by.teachmeskills.springbootproject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class Search {
    private String searchKey;
    private Integer priceFrom;
    private Integer priceTo;
    private String categoryName;
}