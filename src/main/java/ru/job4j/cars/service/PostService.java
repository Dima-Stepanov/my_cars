package ru.job4j.cars.service;

import ru.job4j.cars.model.dao.Post;
import ru.job4j.cars.model.dto.PostDTO;

import java.util.Collection;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.0. Проект "АвтоМаг"
 * PostService interface описывает поведение слоя бизнес логики обработки модели ПОСТ
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 01.04.2023
 */
public interface PostService {
    boolean create(Post post);

    boolean update(Post post);

    Optional<PostDTO> findPostById(int postId);

    Collection<PostDTO> findAllPost();

    Collection<PostDTO> findAllPostLastDayOrderByCreated();

    Collection<PostDTO> findAllPostWithPhotos();

    Collection<PostDTO> findAllPostByBrand(int brandId);
}
