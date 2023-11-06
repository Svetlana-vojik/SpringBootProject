package by.teachmeskills.springbootproject.csv.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCsv {
    private int id;

    @CsvBindByName
    private String name;
    private String imagePath;

    @CsvBindByName
    private int rating;

    private List<ProductCsv> products;
}