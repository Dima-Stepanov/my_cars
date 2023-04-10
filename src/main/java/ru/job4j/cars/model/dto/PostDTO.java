package ru.job4j.cars.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.0. Проект "АвтоМаг"
 * PostDTO модель DTO для отображения на странице
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 10.04.2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "of")
public class PostDTO {
    private int id;
    private String description;
    private LocalDateTime created;
    private LocalDateTime done;
    private long beforePrice;
    private long afterPrice;
    private String carBrand;
    private String carModel;
    private String engine;
    private int owners;
    private int participates;
    private int[] fileId;
}
