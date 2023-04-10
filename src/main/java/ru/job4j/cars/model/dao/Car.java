package ru.job4j.cars.model.dao;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.0. Проект "АвтоМаг"
 * Car модель данных содержит характеристики продаваемого автомобиля.
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 01.04.2023
 */
@Entity
@Table(name = "cars")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = false)
@Getter
@Setter
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String name;
    @ManyToOne
    @JoinColumn(nullable = false,
            name = "model_id",
            foreignKey = @ForeignKey(name = "MODEL_ID_FK"))
    @ToString.Exclude
    private CarModel carModel;
    @ManyToOne
    @JoinColumn(nullable = false,
            name = "engine_id",
            foreignKey = @ForeignKey(name = "ENGINE_ID_FK"))
    @ToString.Exclude
    private Engine engine;
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "history_owner",
            joinColumns = {
                    @JoinColumn(name = "car_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "owner_id", nullable = false, updatable = false)}
    )
    private Set<Owner> owners = new HashSet<>();
}
