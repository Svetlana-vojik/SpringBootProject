package by.teachmeskills.springbootproject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString
@AllArgsConstructor
@Data
@SuperBuilder
public class Order extends BaseEntity {
    private LocalDateTime orderDate;
    private int price;
    private int userId;
    List<Product> products;
}