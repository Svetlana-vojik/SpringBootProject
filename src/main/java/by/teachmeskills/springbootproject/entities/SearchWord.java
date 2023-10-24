package by.teachmeskills.springbootproject.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class SearchWord {
    private String searchString;
    private int paginationNumber;
}