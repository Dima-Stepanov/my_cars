package ru.job4j.cars.model.filemodel;

import lombok.*;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.0. Проект "АвтоМаг"
 * FileDTO (Data Transfer Object)
 * Модель для отображения файла в вид.
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 01.04.2023
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FileDTO {
    private String name;
    @ToString.Exclude
    private byte[] content;
}
