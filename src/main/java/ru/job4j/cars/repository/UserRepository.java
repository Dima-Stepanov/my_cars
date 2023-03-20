package ru.job4j.cars.repository;

import ru.job4j.cars.model.User;

import java.util.Collection;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.0. Проект "АвтоМаг"
 * UserRepository интерфейс описывает поведение хранилища модели User.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 14.03.2023
 */
public interface UserRepository {
    /**
     * Сохранить в базе.
     *
     * @param user пользователь.
     * @return пользователь с id.
     */
    User create(User user);

    /**
     * Найти пользователя по ID
     *
     * @return пользователь.
     */
    Optional<User> findById(int userId);

    /**
     * Обновить в базе пользователя.
     *
     * @param user пользователь.
     */
    void update(User user);

    /**
     * Удалить пользователя по id.
     *
     * @param userId ID
     */
    void delete(int userId);

    /**
     * Найти пользователя по login.
     *
     * @param login login.
     * @return Optional or user.
     */
    Optional<User> findByLogin(String login);

    /**
     * Список пользователь отсортированных по id.
     *
     * @return список пользователей.
     */
    Collection<User> findAllOrderById();

    /**
     * Список пользователей по login LIKE %key%
     *
     * @param key key
     * @return список пользователей.
     */
    Collection<User> findByLikeLogin(String key);
}
