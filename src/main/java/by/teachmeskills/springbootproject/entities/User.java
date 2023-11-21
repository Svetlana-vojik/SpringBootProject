package by.teachmeskills.springbootproject.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @NotNull
    @NotBlank(message = "Email не должен быть пустым")
    @Email(message = "Некорректный адрес электронной почты.")
    @Column
    private String email;

    @NotNull
    @NotBlank(message = "Пароль не должен быть пустым")
    @Column
    private String password;

    @NotNull
    @NotBlank(message = "Имя не должно быть пустым")
    @Pattern(regexp = "[A-Za-z А-Яа-я]+", message = "Некорректное имя.")
    @Column
    private String name;

    @NotNull
    @NotBlank(message = "Фамилия не должна быть пустой")
    @Pattern(regexp = "[A-Za-z А-Яа-я]+", message = "Некорректная фамилия.")
    @Column
    private String surname;

    @NotNull
    @Pattern(regexp = "\\d{4}-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])*", message = "Некорректная дата рождения")
    @Column
    private String birthday;
    @Column
    private int balance;

    @NotNull(message = "Поле не должно быть пустым")
    @Pattern(regexp = "[A-Za-z А-Яа-я0-9\\d]+", message = "Некорректный адрес")
    @Column
    private String address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Order> order;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;
}