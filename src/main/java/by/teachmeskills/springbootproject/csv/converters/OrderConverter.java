package by.teachmeskills.springbootproject.csv.converters;

import by.teachmeskills.springbootproject.csv.dto.OrderCsvDto;
import by.teachmeskills.springbootproject.entities.Order;
import by.teachmeskills.springbootproject.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class OrderConverter {
    private final ProductConverter productConverter;
    private final UserRepository userRepository;

    public OrderCsvDto toCsv(Order order) {
        return Optional.ofNullable(order).map(o -> OrderCsvDto.builder()
                        .id(o.getId())
                        .orderDate(o.getOrderDate().toString())
                        .productList(Optional.ofNullable(o.getProductList()).map(products -> products.stream()
                                .map(productConverter::toCsv).toList()).orElse(List.of()))
                        .price(o.getPrice())
                        .userId(o.getUser().getId())
                        .build())
                .orElse(null);
    }

    public Order fromCsv(OrderCsvDto orderCsv) {
        return Order.builder()
                .user(Optional.ofNullable(userRepository.findById(orderCsv.getUserId()))
                        .orElseThrow(() -> new EntityNotFoundException(String.format("Пользователя с id %d не найдено.", orderCsv.getUserId()))))
                .price(orderCsv.getPrice())
                .orderDate(LocalDate.parse(orderCsv.getOrderDate()))
                .build();
    }
}