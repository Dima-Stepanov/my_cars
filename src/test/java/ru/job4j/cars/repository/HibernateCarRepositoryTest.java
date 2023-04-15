package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import ru.job4j.cars.model.dao.*;

import javax.persistence.PersistenceException;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.0. Проект "АвтоМаг"
 * HibernateCarRepository TEST
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 07.04.2023
 */
class HibernateCarRepositoryTest {
    private static SessionFactory sessionFactory;
    private static CrudRepository crud;
    private static HibernateCarRepository carRepository;
    private CarBrand carBrand;
    private CarModel carModel;
    private Engine engine;
    private User user1;
    private User user2;
    private Owner owner1;
    private Owner owner2;

    private static void deleteEntity() {
        crud.run("delete from Car as c where c.id >:cId",
                Map.of("cId", 0));
        crud.run("delete from Owner as o where o.id >:oId",
                Map.of("oId", 0));
        crud.run("delete from User as u where u.id >:uId",
                Map.of("uId", 0));
        crud.run("delete from Engine as e where e.id >:eId",
                Map.of("eId", 0));
        crud.run("delete from CarModel as cm where cm.id >: cmId",
                Map.of("cmId", 0));
        crud.run("delete from CarBrand as cb where cb.id >:cbId",
                Map.of("cbId", 0));
    }

    @AfterEach
    public void deleteAfter() {
        deleteEntity();
    }

    @BeforeEach
    public void initEntity() {
        carBrand = new CarBrand(0, "CarBrand");
        crud.run(session -> session.persist(carBrand));

        carModel = new CarModel(0, "CarModel", carBrand);
        crud.run(session -> session.persist(carModel));

        engine = new Engine(0, "Engine");
        crud.run(session -> session.persist(engine));

        user1 = new User(0, "User1Login", "User1Pass");
        user2 = new User(0, "User2Login", "User2Pass");
        crud.run(session -> session.persist(user1));
        crud.run(session -> session.persist(user2));

        owner1 = new Owner(0, "Owner1", user1);
        owner2 = new Owner(0, "Owner2", user2);
        crud.run(session -> session.persist(owner1));
        crud.run(session -> session.persist(owner2));
    }

    @BeforeAll
    public static void deleteBeforeAll() {
        deleteEntity();
    }

    @BeforeAll
    public static void initCarRepository() {
        sessionFactory = HibernateConfigurationTest.getSessionFactory();
        crud = new CrudRepository(sessionFactory);
        carRepository = new HibernateCarRepository(crud);
    }

    @AfterAll
    public static void closeResource() {
        if (!sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }

    @Test
    void whenCreateCarThenReturnInDbOptionalCarAndCarIdGreaterZero() {
        var car = new Car(0, "Car", carModel, engine, Set.of(owner1, owner2));
        carRepository.create(car);

        var carIndDb = carRepository.findCarById(car.getId());

        assertThat(car.getId()).isGreaterThan(0);
        assertThat(carIndDb).usingRecursiveComparison().isEqualTo(Optional.of(car));
    }

    @Test
    void whenCreateCarEngineNullThenReturnPersistentException() {
        var car = new Car(0, "Car", carModel, null, Set.of(owner1, owner2));
        assertThatThrownBy(() -> carRepository.create(car))
                .isInstanceOf(PersistenceException.class);
    }

    @Test
    void whenCreateCarOwnersEmptyThenReturnNewCarAndIdGreater() {
        var car = new Car(0, "Car", carModel, engine, new HashSet<>());
        carRepository.create(car);

        var carIndDb = carRepository.findCarById(car.getId());

        assertThat(car.getId()).isGreaterThan(0);
        assertThat(carIndDb).usingRecursiveComparison().isEqualTo(Optional.of(car));
    }

    @Test
    void whenFindCarByIdThenReturnOptionalEmpty() {
        var carInDb = carRepository.findCarById(-1);
        assertThat(carInDb).isEmpty();
    }

    @Test
    void whenUpdateNameThenFindByIdReturnUpdateCar() {
        var car = new Car(0, "Car", carModel, engine, Set.of(owner1, owner2));
        carRepository.create(car);

        car.setName("newCar");

        carRepository.update(car);
        var carInDb = carRepository.findCarById(car.getId());

        assertThat(carInDb).usingRecursiveComparison().isEqualTo(Optional.of(car));
    }

    @Test
    void whenUpdateCarEngineThenFindByIdReturnUpdateCar() {
        var car = new Car(0, "Car", carModel, engine, Set.of(owner1, owner2));
        carRepository.create(car);
        var newEngine = new Engine(0, "newEngine");
        crud.run(session -> session.persist(newEngine));
        car.setEngine(engine);

        carRepository.update(car);
        var carInDb = carRepository.findCarById(car.getId());

        assertThat(carInDb).usingRecursiveComparison().isEqualTo(Optional.of(car));
    }

    @Test
    void whenUpdateCarModelThenFindByIdReturnUpdateCar() {
        var car = new Car(0, "Car", carModel, engine, Set.of(owner1, owner2));
        carRepository.create(car);
        var newCaModel = new CarModel(0, "newCarModel", carBrand);
        crud.run(session -> session.persist(newCaModel));
        car.setCarModel(newCaModel);

        carRepository.update(car);
        var carInDb = carRepository.findCarById(car.getId());

        assertThat(carInDb).usingRecursiveComparison().isEqualTo(Optional.of(car));
    }

    @Test
    void whenUpdateCarOwnersThenFindByIdReturnUpdateCar() {
        var car = new Car(0, "Car", carModel, engine, Set.of(owner1, owner2));
        carRepository.create(car);
        car.setOwners(Set.of(owner2));

        carRepository.update(car);
        var catInDb = carRepository.findCarById(car.getId());

        assertThat(catInDb).usingRecursiveComparison().isEqualTo(Optional.of(car));
    }

    @Test
    void whenDeleteCarThenFindCarByIdReturnOptionalEmpty() {
        var car = new Car(0, "Car", carModel, engine, Set.of(owner1, owner2));
        carRepository.create(car);

        carRepository.delete(car.getId());
        var carInDb = carRepository.findCarById(car.getId());

        assertThat(carInDb).isEmpty();
    }

    @Test
    void whenFindAllCarThenReturnCollectionEmpty() {
        var cars = carRepository.findAllCar();

        assertThat(cars.isEmpty()).isTrue();
    }

    @Test
    void whenFindAllCarThenReturnCollectionCars() {
        var car1 = new Car(0, "Car1", carModel, engine, Set.of(owner1, owner2));
        var car2 = new Car(0, "Car2", carModel, engine, Set.of(owner1, owner2));
        carRepository.create(car2);
        carRepository.create(car1);

        var expectedCars = List.of(car2, car1);
        var actualCars = carRepository.findAllCar();

        assertThat(actualCars).usingRecursiveComparison().isEqualTo(expectedCars);
    }
}