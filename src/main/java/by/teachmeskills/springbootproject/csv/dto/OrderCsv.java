package by.teachmeskills.springbootproject.csv.dto;


import by.teachmeskills.springbootproject.entities.Order;
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
public class OrderCsv {
    private int id;

    @CsvBindByName
    private String orderDate;

    @CsvBindByName
    private List<ProductCsv> productList;

    @CsvBindByName
    private int price;

    @CsvBindByName
    private int userId;
}