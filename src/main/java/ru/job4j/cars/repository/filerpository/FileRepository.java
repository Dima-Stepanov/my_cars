package ru.job4j.cars.repository.filerpository;

import ru.job4j.cars.model.filemode.File;

import java.util.List;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.0. Проект "АвтоМаг"
 * FileRepository описывает поведение хранилища модели File.
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 01.04.2023
 */
public interface FileRepository {

    /**
     * Сохранение файла
     *
     * @param file File
     * @return File.ID > 0, or File.ID==0
     */
    File save(File file);

    /**
     * Поиск файла по его ID
     *
     * @param fileId File ID
     * @return Optional<File> or Optional.empty
     */
    Optional<File> findById(int fileId);

    /**
     * Удаление файлов по его ID
     *
     * @param fileId File ID
     */
    void deleteFileById(int fileId);

    /**
     * Поиск всех файлов принадлежащих одному посту.
     *
     * @param postId Post ID
     * @return List<Files>
     */
    List<File> findAllFileByPostOrderById(int postId);
}
