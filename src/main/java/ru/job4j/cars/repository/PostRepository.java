package ru.job4j.cars.repository;

import ru.job4j.cars.model.dao.Post;

import java.util.Collection;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.0. Проект "АвтоМаг"
 * PostRepository интерфейс описывает поведение хранилища модели Post.
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 02.04.2023
 */
public interface PostRepository {
    Post create(Post post);

    Optional<Post> findPostById(int postId);

    void update(Post post);

    void delete(int postId);

    Collection<Post> findAllPost();

    /**
     * Поиск всех объявлений за последний день
     *
     * @return Collection<Post>
     */
    Collection<Post> findAllPostLastDayOrderByCreated();

    /**
     * Поиск объявлений только с фото
     *
     * @return Collection<Post>
     */
    Collection<Post> findAllPostWithPhotos();

    /**
     * Поиск автомобилей определенной марки.
     *
     * @param brandId int
     * @return Collection<Post>
     */
    Collection<Post> findAllPostByBrand(int brandId);
}
