package ru.job4j.cars.mapper;

import ru.job4j.cars.model.dao.File;
import ru.job4j.cars.model.dao.Post;
import ru.job4j.cars.model.dao.PriceHistory;
import ru.job4j.cars.model.dto.PostDTO;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.0. Проект "АвтоМаг"
 * PostMapper класс преобразования DAO модели в DTO.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 12.04.2023
 */
public class PostMapper {
    /**
     * Метод возвращает PostDTO из Post
     *
     * @param post Post
     * @return PostDTO
     */
    public PostDTO getPostDto(Post post) {
        var allPriceHistory = getAllPriceHistory(post.getPriceHistory());
        var filesId = getAllFileID(post.getFiles());
        return PostDTO.of()
                .description(post.getDescription())
                .created(post.getCreated())
                .done(post.getDone())
                .beforePrice(allPriceHistory.getBefore())
                .afterPrice(allPriceHistory.getAfter())
                .carModel(post.getCar().getCarModel().getName())
                .carBrand(post.getCar().getCarModel().getCarBrand().getName())
                .engine(post.getCar().getEngine().getName())
                .owners(post.getCar().getOwners().size())
                .participates(post.getParticipates().size())
                .fileId(filesId)
                .build();
    }

    /**
     * Метод возвращает модель PriceHistory из коллекции историй цены.
     * Итоговый результат самая первая и самая последняя цена в модели
     *
     * @param priceHistories Set<PriceHistory>
     * @return PriceHistory
     */
    private PriceHistory getAllPriceHistory(Collection<PriceHistory> priceHistories) {
        long beforePrice = 0;
        long afterPrice = 0;
        for (PriceHistory priceHistory : priceHistories) {
            if (beforePrice == 0) {
                beforePrice = priceHistory.getBefore();
            }
            if (priceHistory.getAfter() == 0) {
                afterPrice = priceHistory.getBefore();
                break;
            }
            afterPrice = priceHistory.getAfter();
        }
        return new PriceHistory(-1, beforePrice, afterPrice,
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }

    /**
     * Метод возвращает массив id файлов.
     *
     * @param files Collection<File>
     * @return int[]
     */
    private int[] getAllFileID(Collection<File> files) {
        return files.stream()
                .mapToInt(File::getId).toArray();
    }
}
