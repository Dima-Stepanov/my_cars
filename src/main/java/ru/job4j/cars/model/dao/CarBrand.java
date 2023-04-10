package ru.job4j.cars.model.dao;

import lombok.*;

import javax.persistence.*;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.0. Проект "АвтоМаг"
 * Marc модель данных описывает марку автомобиля.
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 02.04.2023
 */
@Entity
@Table(name = "car_brands")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Getter
@Setter
public class CarBrand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    @Column(nullable = false, unique = true)
    private String name;
}
