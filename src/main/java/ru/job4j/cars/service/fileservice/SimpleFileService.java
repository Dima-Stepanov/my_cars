package ru.job4j.cars.service.fileservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.filemode.File;
import ru.job4j.cars.model.filemode.FileDTO;
import ru.job4j.cars.repository.filerpository.FileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.0. Проект "АвтоМаг"
 * SimpleFileService класс реализации бизнес логики,
 * обработки и представления модели File
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 01.04.2023
 */
@Service
@Slf4j
public class SimpleFileService implements FileService {
    private final FileRepository fileRepository;
    private final String storageDirectory;

    public SimpleFileService(FileRepository fileRepository,
                             @Value("${file.directory}") String storageDirectory) {
        this.fileRepository = fileRepository;
        this.storageDirectory = storageDirectory;
        createStorageDirectory(storageDirectory);
    }

    /**
     * Метод создает директорию для хранения файлов если ее еще нет.
     *
     * @param patch Name directory
     */
    private void createStorageDirectory(String patch) {
        try {
            Files.createDirectories(Path.of(patch));
        } catch (IOException e) {
            log.error("Create directory:{}, error:{}", patch, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод генерирует уникальное имя файла,
     * для сохранения в файловую систему.
     *
     * @param sourceName file name
     * @return new file name
     */
    private String getNewFilePath(String sourceName) {
        return storageDirectory + java.io.File.separator
                + UUID.randomUUID() + sourceName;
    }

    /**
     * Метод создает файл и записывает в него контент.
     *
     * @param path    file name String
     * @param content byte[]
     */
    private void writeFileBytes(String path, byte[] content) {
        try {
            Files.write(Path.of(path), content);
        } catch (IOException e) {
            log.error("Save file:{}, error:{}", path, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод читает массив байт из файла
     *
     * @param path File name String
     * @return byte[]
     */
    private byte[] readFileAsBytes(String path) {
        try {
            return Files.readAllBytes(Path.of(path));
        } catch (IOException e) {
            log.error("Read file:{}, error:{}", path, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод удаляет файл из файловой системы.
     *
     * @param path String
     */
    private void deleteFile(String path) {
        try {
            Files.deleteIfExists(Path.of(path));
        } catch (IOException e) {
            log.error("Delete file:{}, error:{}", path, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public File save(FileDTO fileDTO, int postId) {
        var path = getNewFilePath(fileDTO.getName());
        writeFileBytes(path, fileDTO.getContent());
        var post = new Post();
        post.setId(postId);
        var file = new File(0, fileDTO.getName(), path, post);
        return fileRepository.save(file);
    }

    @Override
    public Optional<FileDTO> getFileById(int fileId) {
        var fileOptional = fileRepository.findById(fileId);
        if (fileOptional.isEmpty()) {
            return Optional.empty();
        }
        var content = readFileAsBytes(fileOptional.get().getPath());
        return Optional.of(new FileDTO(fileOptional.get().getName(), content));
    }

    @Override
    public boolean deleteById(int fileId) {
        var fileOptional = fileRepository.findById(fileId);
        if (fileOptional.isEmpty()) {
            return false;
        }
        deleteFile(fileOptional.get().getPath());
        fileRepository.deleteFileById(fileId);
        return true;
    }

    @Override
    public List<FileDTO> getAllFileByPostId(int postId) {
        var filesByPost = fileRepository.findAllFileByPostOrderById(postId);
        var filesDTObyPost = new ArrayList<FileDTO>();
        for (File file : filesByPost) {
            var content = readFileAsBytes(file.getPath());
            filesDTObyPost.add(new FileDTO(file.getName(), content));
        }
        return filesDTObyPost;
    }
}
