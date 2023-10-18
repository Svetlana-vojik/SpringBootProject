package by.teachmeskills.springbootproject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
@SuperBuilder
@NoArgsConstructor
public class User extends BaseEntity {
    private String email;
    private String password;
    private String name;
    private String surname;
    private LocalDate birthday;
    private int balance;
    private String address;

    public User(String email, String password, String name, String surname, String birthday, int balance, String address) {
    }
}