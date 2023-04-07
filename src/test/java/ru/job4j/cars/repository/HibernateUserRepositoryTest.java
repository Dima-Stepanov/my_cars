package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import ru.job4j.cars.model.User;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.0. Проект "АвтоМаг"
 * HibernateUserRepository TEST
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 05.04.2023
 */
class HibernateUserRepositoryTest {
    private static SessionFactory sf;
    private static HibernateUserRepository userRepository;

    private void deleteUsers() {
        var crud = new CrudRepository(sf);
        crud.run("delete User as u where u.id >:uId",
                Map.of("uId", 0));
    }


    @BeforeAll
    public static void initRepository() {
        sf = HibernateConfigurationTest.getSessionFactory();
        var crud = new CrudRepository(sf);
        userRepository = new HibernateUserRepository(crud);
    }

    @AfterAll
    public static void closeSF() {
        if (!sf.isClosed()) {
            sf.close();
        }
    }

    @AfterEach
    public void clearUserDBAfter() {
        deleteUsers();
    }

    @Test
    void whenCreateNewUserThenReturnUserIdByGreaterZero() {
        var user = new User(0, "login", "password");
        userRepository.create(user);
        var userInDb = userRepository.findById(user.getId());

        assertThat(user.getId()).isGreaterThan(0);
        assertThat(userInDb).usingRecursiveComparison().isEqualTo(Optional.of(user));
    }

    @Test
    void whenCreateNewUserThenUniqueException() {
        var user1 = new User(0, "login", "password");
        var user2 = new User(0, "login", "password");
        userRepository.create(user1);
        assertThatThrownBy(() -> userRepository.create(user2))
                .isInstanceOf(PersistenceException.class);
    }

    @Test
    void whenFindByIdUserThenReturnOptionalUser() {
        var user = new User(0, "login", "password");
        userRepository.create(user);
        var userInDb = userRepository.findById(user.getId());
        assertThat(userInDb).usingRecursiveComparison().isEqualTo(Optional.of(user));
    }

    @Test
    void whenFindByIdUserThenReturnEmptyOptional() {
        var userInDb = userRepository.findById(-1);
        assertThat(userInDb).isEmpty();
    }

    @Test
    void whenUpdateUserThenReturnInDbUpdateUser() {
        var user = new User(0, "login", "password");
        userRepository.create(user);

        user.setLogin("newLogin");
        user.setPassword("newPassword");

        userRepository.update(user);
        var userInDb = userRepository.findById(user.getId());

        assertThat(userInDb).usingRecursiveComparison().isEqualTo(Optional.of(user));
    }

    @Test
    void whenUpdateUserAnyIdThenNotUpdateUser() {
        var user = new User(0, "login", "password");
        userRepository.create(user);

        user.setId(user.getId() + 1);

        assertThatThrownBy(() -> userRepository.update(user))
                .isInstanceOf(PersistenceException.class);
    }

    @Test
    void whenDeleteUserByIdThenReturnFindByIdOptionalEmpty() {
        var user = new User(0, "login", "password");
        userRepository.create(user);

        userRepository.delete(user.getId());
        var userInDb = userRepository.findById(user.getId());

        assertThat(userInDb).isEmpty();
    }

    @Test
    void whenFindByLoginAndPasswordThenReturnUserOptional() {
        var user = new User(0, "login", "password");
        userRepository.create(user);

        var userInDb = userRepository.findByLoginAndPassword(user.getLogin(), user.getPassword());

        assertThat(userInDb).usingRecursiveComparison().isEqualTo(Optional.of(user));
    }

    @Test
    void whenFindByLoginAndPasswordThenReturnOptionalEmpty() {
        var user = new User(0, "login", "password");

        var userInDb = userRepository.findByLoginAndPassword(user.getLogin(), user.getPassword());

        assertThat(userInDb).isEmpty();
    }

    @Test
    void whenFindAllOrderByIdThenReturnCollectionUser() {
        var user1 = new User(0, "login1", "password1");
        var user2 = new User(0, "login2", "password2");
        userRepository.create(user1);
        userRepository.create(user2);

        var expectedList = List.of(user1, user2);
        var actualList = userRepository.findAllOrderById();

        assertThat(actualList).usingRecursiveComparison().isEqualTo(expectedList);
    }

    @Test
    void whenFindAllOrderByIdThenReturnCollectionEmpty() {
        var actualList = userRepository.findAllOrderById();

        assertThat(actualList.isEmpty()).isTrue();
    }

    @Test
    void whenFindByLikeLoginThenReturnCollectionUser() {
        var user1 = new User(0, "login1", "password1");
        var user2 = new User(0, "login2", "password2");
        userRepository.create(user1);
        userRepository.create(user2);

        var expectedList = List.of(user1, user2);
        var actualList = userRepository.findByLikeLogin("log");

        assertThat(actualList).usingRecursiveComparison().isEqualTo(expectedList);
    }

    @Test
    void whenFindByLikeLoginThenReturnCollectionOneUser() {
        var user1 = new User(0, "***1***", "password1");
        var user2 = new User(0, "***2***", "password2");
        userRepository.create(user1);
        userRepository.create(user2);

        var expectedList = List.of(user2);
        var actualList = userRepository.findByLikeLogin("2");

        assertThat(actualList).usingRecursiveComparison().isEqualTo(expectedList);
    }
}