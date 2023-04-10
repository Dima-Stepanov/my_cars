package ru.job4j.cars.model.dao;

import lombok.*;

import javax.persistence.*;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.0. Проект "АвтоМаг"
 * File модель данных описывает модель хранимого файла,
 * который будет отображаться в видах.
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 01.04.2023
 */
@Entity
@Table(name = "files")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = false)
@Getter
@Setter
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    @EqualsAndHashCode.Include
    private String path;
}
