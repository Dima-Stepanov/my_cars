package ru.job4j.cars.repository.filerpository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.filemodel.File;
import ru.job4j.cars.repository.CrudRepository;

import java.util.Map;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.0. Проект "АвтоМаг"
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 01.04.2023
 */
@Repository
@AllArgsConstructor
public class HibernateFileRepository implements FileRepository {
    private final CrudRepository crudRepository;

    /**
     * Сохранение файла
     *
     * @param file File
     * @return File.ID > 0, or File.ID==0
     */
    @Override
    public File save(File file) {
        crudRepository.run(session -> session.persist(file));
        return file;
    }

    /**
     * Поиск файла по его ID
     *
     * @param fileId File ID
     * @return Optional<File> or Optional.empty
     */
    @Override
    public Optional<File> findById(int fileId) {
        return crudRepository.optional(
                "FROM File AS f WHERE f.id =:fileId",
                File.class,
                Map.of("fileId", fileId)
        );
    }

    /**
     * Удаление файлов по его ID
     *
     * @param fileId File ID
     */
    @Override
    public void deleteFileById(int fileId) {
        crudRepository.run(
                "DELETE FROM File AS f WHERE f.id =:fileId",
                Map.of("fileId", fileId)
        );
    }
}
