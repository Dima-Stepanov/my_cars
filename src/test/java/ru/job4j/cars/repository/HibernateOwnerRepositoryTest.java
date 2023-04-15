package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import ru.job4j.cars.model.dao.Owner;
import ru.job4j.cars.model.dao.User;

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
 * HibernateOwnerRepository TEST
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 06.04.2023
 */
class HibernateOwnerRepositoryTest {
    private static SessionFactory sf;
    private static HibernateOwnerRepository ownerRepository;
    private User user1;
    private User user2;

    private static void deleteOwnerUser() {
        var crud = new CrudRepository(sf);
        crud.run("delete from Owner as o where o.id >:oId",
                Map.of("oId", 0));
        crud.run("delete from User as u where u.id >:uId",
                Map.of("uId", 0));
    }

    @BeforeAll
    public static void initRepository() {
        sf = HibernateConfigurationTest.getSessionFactory();
        var crud = new CrudRepository(sf);
        ownerRepository = new HibernateOwnerRepository(crud);
    }

    @BeforeAll
    public static void deleteBeforeAll() {
        deleteOwnerUser();
    }

    @AfterAll
    public static void closeSF() {
        if (!sf.isClosed()) {
            sf.close();
        }
    }

    @BeforeEach
    public void addUserToDataBase() {
        user1 = new User(0, "user1", "pass1");
        user2 = new User(0, "user2", "pass2");
        var crud = new CrudRepository(sf);
        crud.run(session -> session.persist(user1));
        crud.run(session -> session.persist(user2));
    }


    @AfterEach
    public void deleteAfter() {
        deleteOwnerUser();
    }

    @Test
    void whenCreateOwnerThenReturnOwnerIdGreaterZero() {
        var owner = new Owner(0, "owner", user1);
        ownerRepository.create(owner);

        var ownerInDb = ownerRepository.findOwnerById(owner.getId());

        assertThat(ownerInDb).usingRecursiveComparison().isEqualTo(Optional.of(owner));
        assertThat(owner.getId()).isGreaterThan(0);
    }

    @Test
    void whenCreateOwnerUniqueUserThenReturnException() {
        var owner = new Owner(0, "owner", user1);
        ownerRepository.create(owner);
        var owner2 = new Owner(0, "owner2", user1);

        assertThatThrownBy(() -> ownerRepository.create(owner2))
                .isInstanceOf(PersistenceException.class);

    }

    @Test
    void whenFindOwnerByIdThenReturnOptionalEmpty() {
        var ownerInDb = ownerRepository.findOwnerById(-1);

        assertThat(ownerInDb).isEmpty();
    }

    @Test
    void whenUpdateOwnerNameThenReturnFindByIdActualOwner() {
        var owner = new Owner(0, "owner", user1);
        ownerRepository.create(owner);
        owner.setName("newOwner");

        ownerRepository.update(owner);
        var ownerInDb = ownerRepository.findOwnerById(owner.getId());

        assertThat(ownerInDb).usingRecursiveComparison().isEqualTo(Optional.of(owner));
        assertThat(ownerInDb.get().getUser().getLogin()).isEqualTo(user1.getLogin());
    }

    @Test
    void whenUpdateOwnerUniqueUserThenReturnException() {
        var owner = new Owner(0, "owner", user1);
        ownerRepository.create(owner);
        var owner2 = new Owner(0, "owner2", user2);
        ownerRepository.create(owner2);

        owner.setUser(user2);

        assertThatThrownBy(() -> ownerRepository.update(owner))
                .isInstanceOf(PersistenceException.class);
    }

    @Test
    void whenDeleteOwnerByIdThenFindByIdReturnOptionalEmpty() {
        var owner = new Owner(0, "owner", user1);
        ownerRepository.create(owner);

        ownerRepository.delete(owner.getId());
        var ownerInDb = ownerRepository.findOwnerById(owner.getId());

        assertThat(ownerInDb).isEmpty();
    }

    @Test
    void whenFindAllOwnerThenReturnCollectionOwners() {
        var owner1 = new Owner(0, "owner1", user1);
        ownerRepository.create(owner1);
        var owner2 = new Owner(0, "owner2", user2);
        ownerRepository.create(owner2);

        var expectedOwners = List.of(owner1, owner2);
        var actualOwners = ownerRepository.findAllOwner();

        assertThat(actualOwners).usingRecursiveComparison().isEqualTo(expectedOwners);
    }

    @Test
    void whenFindAllOwnerThenReturnCollectionEmpty() {
        var actualOwners = ownerRepository.findAllOwner();
        assertThat(actualOwners.isEmpty()).isTrue();
    }
}