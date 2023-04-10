package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.dao.User;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.0. Проект "АвтоМаг"
 * HibernateUserRepository реализация хранилища модели User
 * при помощи Hibernate HQL
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 14.03.2023
 */
@Repository
@AllArgsConstructor
public class HibernateUserRepository implements UserRepository {
    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param user пользователь.
     * @return пользователь с id.
     */
    @Override
    public User create(User user) {
        crudRepository.run(session -> session.persist(user));
        return user;
    }

    /**
     * Найти пользователя по ID
     *
     * @return пользователь.
     */
    @Override
    public Optional<User> findById(int userId) {
        return crudRepository.optional(
                "from User as u where u.id =:userId",
                User.class,
                Map.of("userId", userId)
        );
    }

    /**
     * Обновить в базе пользователя.
     *
     * @param user пользователь.
     */
    @Override
    public void update(User user) {
        crudRepository.run(session -> session.merge(user));
    }

    /**
     * Удалить пользователя по id.
     *
     * @param userId ID
     */
    @Override
    public void delete(int userId) {
        crudRepository.run(
                "delete from User as u where u.id =:userId",
                Map.of("userId", userId)
        );
    }


    /**
     * Найти пользователя по login.
     *
     * @param login login.
     * @return Optional or user.
     */
    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudRepository.optional(
                "from User as u where u.login =:login and u.password =:password",
                User.class,
                Map.of("login", login,
                        "password", password)
        );
    }

    /**
     * Список пользователь отсортированных по id.
     *
     * @return список пользователей.
     */
    @Override
    public Collection<User> findAllOrderById() {
        return crudRepository.query(
                "from User order by id asc",
                User.class
        );
    }

    /**
     * Список пользователей по login LIKE %key%
     *
     * @param key key
     * @return список пользователей.
     */
    @Override
    public Collection<User> findByLikeLogin(String key) {
        return crudRepository.query(
                "from User as u where u.login LIKE :key",
                User.class,
                Map.of("key", "%" + key + "%")
        );
    }
}
