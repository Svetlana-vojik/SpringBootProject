package by.teachmeskills.springbootproject.csv.converters;

import by.teachmeskills.springbootproject.csv.dto.UserCsv;
import by.teachmeskills.springbootproject.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class UserConverter {
    private final OrderConverter orderConverter;


    public UserCsv toCsv(User user) {
        return Optional.ofNullable(user).map(u -> UserCsv.builder()
                        .id(u.getId())
                        .name(u.getName())
                        .surname(u.getSurname())
                        .birthday(LocalDate.parse(u.getBirthday()))
                        .email(u.getEmail())
                        .balance(u.getBalance())
                        .password(u.getPassword())
                        .orders(Optional.ofNullable(u.getOrder()).map(orders -> orders.stream()
                                .map(orderConverter::toCsv).toList()).orElse(List.of()))
                        .build())
                .orElse(null);
    }

    public User fromCsv(UserCsv userCsv) {
        return Optional.ofNullable(userCsv).map(uc -> User.builder()
                        .name(uc.getName())
                        .surname(uc.getSurname())
                        .birthday(String.valueOf(uc.getBirthday()))
                        .email(uc.getEmail())
                        .password(uc.getPassword())
                        .balance(uc.getBalance())
                        .build())
                .orElse(null);
    }
}