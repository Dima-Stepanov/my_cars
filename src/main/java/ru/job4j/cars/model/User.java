package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.0. Проект "АвтоМаг"
 * User - JPA модель данных пользователей.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 14.03.2023
 */
@Entity
@Table(name = "auto_users")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    @Column(nullable = false, unique = true)
    @EqualsAndHashCode.Include
    private String login;
    @Column(nullable = false)
    private String password;
}
