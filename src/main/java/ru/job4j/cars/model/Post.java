package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.0. Проект "АвтоМаг"
 * Post описывает модель данных объявления
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 21.03.2023
 */
@Entity
@Table(name = "auto_posts")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = false)
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private LocalDateTime created = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(nullable = false, name = "auto_user_id")
    private User user;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "post_id")
    @Singular("addPriceHistory")
    private List<PriceHistory> priceHistory = new ArrayList<>();

    @ToString.Exclude
    @ManyToMany
    @JoinTable(
            name = "participates",
            joinColumns = {@JoinColumn(nullable = false, name = "post_id")},
            inverseJoinColumns = {@JoinColumn(nullable = false, name = "user_id")}
    )
    @Singular("addParticipate")
    private List<User> participates = new ArrayList<>();

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(nullable = false, name = "car_id")
    private Car car;
}
