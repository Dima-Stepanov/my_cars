package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.0. Проект "АвтоМаг"
 * CarModel модель описывает модель автомобиля.
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 02.04.2023
 */
@Entity
@Table(name = "car_models")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = false)
@Getter
@Setter
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    @Column(nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(nullable = false,
            name = "brand_id",
            foreignKey = @ForeignKey(name = "BRAND_ID_FK"))
    @ToString.Exclude
    private CarBrand carBrand;
}
