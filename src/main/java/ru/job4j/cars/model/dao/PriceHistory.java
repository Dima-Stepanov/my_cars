package ru.job4j.cars.model.dao;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.0. Проект "АвтоМаг"
 * PriceHistory модель описывает историю изменения цены объявления.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 21.03.2023
 */
@Entity
@Table(name = "PRICE_HISTORY")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class PriceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    @EqualsAndHashCode.Exclude
    private long before;
    @Column(nullable = false)
    @EqualsAndHashCode.Exclude
    private long after;
    @EqualsAndHashCode.Exclude
    private LocalDateTime created = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
}
