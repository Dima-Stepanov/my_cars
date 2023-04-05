package ru.job4j.cars.service.fileservice;

import ru.job4j.cars.model.filemodel.File;
import ru.job4j.cars.model.filemodel.FileDTO;

import java.util.Optional;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.0. Проект "АвтоМаг"
 * FileService интерфейс описывает поведение бизнес лиги обработки модели File.
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 01.04.2023
 */
public interface FileService {
    File save(FileDTO fileDTO);

    Optional<FileDTO> getFileById(int fileId);

    boolean deleteById(int fileId);
}
