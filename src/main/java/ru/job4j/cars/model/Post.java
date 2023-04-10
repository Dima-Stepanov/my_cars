package ru.job4j.cars.model;

import lombok.*;
import ru.job4j.cars.model.filemodel.File;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

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
@Builder(builderMethodName = "of")
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
    private LocalDateTime done;
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
    /**
     * История изменения цен авто
     */
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "post_id", foreignKey = @ForeignKey(name = "FK_POST_PRICE_HISTORY"))
    @Singular("priceHistoryAdd")
    private Set<PriceHistory> priceHistory = new HashSet<>();
    /**
     * Подписчики на объявление
     */
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "participates",
            joinColumns = {@JoinColumn(nullable = false, name = "post_id")},
            inverseJoinColumns = {@JoinColumn(nullable = false, name = "user_id")}
    )
    @Singular("participateAdd")
    private Set<User> participates = new HashSet<>();

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "car_id", foreignKey = @ForeignKey(name = "CAR_ID_FK"))
    private Car car;
    /**
     * Фото обь явления
     */
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(nullable = false, name = "post_id", unique = true, foreignKey = @ForeignKey(name = "FK_POST_FILE"))
    @Singular("filesAdd")
    private Set<File> files = new HashSet<>();
}
