package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.User;

import java.util.Collection;
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
@AllArgsConstructor
public class HibernateUserRepository implements UserRepository {
    private final SessionFactory sessionFactory;

    /**
     * Сохранить в базе.
     *
     * @param user пользователь.
     * @return пользователь с id.
     */
    @Override
    public User create(User user) {
        var session = sessionFactory.openSession();
        try (session) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
        return user;
    }

    /**
     * Обновить в базе пользователя.
     *
     * @param user пользователь.
     */
    @Override
    public void update(User user) {
        var session = sessionFactory.openSession();
        try (session) {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    /**
     * Удалить пользователя по id.
     *
     * @param userId ID
     */
    @Override
    public void delete(int userId) {
        var session = sessionFactory.openSession();
        try (session) {
            session.beginTransaction();
            session.createQuery("DELETE User AS u WHERE u.id =:userId")
                    .setParameter("userId", userId)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    /**
     * Список пользователь отсортированных по id.
     *
     * @return список пользователей.
     */
    @Override
    public Collection<User> findAllOrderById() {
        Collection<User> result;
        var session = sessionFactory.openSession();
        try (session) {
            session.beginTransaction();
            var query = session.createQuery("from User as u order by u.id", User.class);
            result = query.getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
        return result;
    }

    /**
     * Найти пользователя по ID
     *
     * @return пользователь.
     */
    @Override
    public Optional<User> findById(int userId) {
        Optional<User> result;
        var session = sessionFactory.openSession();
        try (session) {
            session.beginTransaction();
            var query = session.createQuery("from User as u where u.id =:userId", User.class)
                    .setParameter("userId", userId);
            result = query.uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
        return result;
    }

    /**
     * Список пользователей по login LIKE %key%
     *
     * @param key key
     * @return список пользователей.
     */
    @Override
    public Collection<User> findByLikeLogin(String key) {
        Collection<User> result;
        var session = sessionFactory.openSession();
        try (session) {
            session.beginTransaction();
            var query = session.createQuery("from User as u where u.login LIKE :key", User.class)
                    .setParameter("key", '%' + key + '%');
            result = query.getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
        return result;
    }

    /**
     * Найти пользователя по login.
     *
     * @param login login.
     * @return Optional or user.
     */
    @Override
    public Optional<User> findByLogin(String login) {
        Optional<User> result;
        var session = sessionFactory.openSession();
        try (session) {
            session.beginTransaction();
            var query = session
                    .createQuery("from User as u where u.login =:login", User.class)
                    .setParameter("login", login);
            result = query.uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
        return result;
    }
}
