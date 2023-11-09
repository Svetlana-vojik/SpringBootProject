package by.teachmeskills.springbootproject.csv.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCsvDto {
    private int id;

    @CsvBindByName
    private String name;

    @CsvBindByName
    private String description;

    @CsvBindByName
    private int price;

    @CsvBindByName
    private String imagePath;

    @CsvBindByName
    private String categoryName;
}