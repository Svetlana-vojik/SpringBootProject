package by.teachmeskills.springbootproject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
public class PaginationParams {
    private int pageNumber;
    private int pageSize;
}