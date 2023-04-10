package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.dao.Engine;

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
 * HibernateEngineRepository TEST
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 06.04.2023
 */
class HibernateEngineRepositoryTest {
    private static SessionFactory sf;
    private static HibernateEngineRepository engineRepository;

    private void deleteEngine() {
        var crud = new CrudRepository(sf);
        crud.run("delete from Engine as e where e.id >:eId",
                Map.of("eId", 0));
    }

    @BeforeAll
    public static void initRepository() {
        sf = HibernateConfigurationTest.getSessionFactory();
        var crud = new CrudRepository(sf);
        engineRepository = new HibernateEngineRepository(crud);
    }

    @AfterAll
    public static void closeSF() {
        if (!sf.isClosed()) {
            sf.close();
        }
    }

    @AfterEach
    public void deleteEach() {
        deleteEngine();
    }

    @Test
    void whenCreateEngineThenResultEngineByGreaterZero() {
        var engine = new Engine(0, "engine");
        engineRepository.create(engine);
        var engineInDb = engineRepository.findEngineById(engine.getId());

        assertThat(engineInDb).usingRecursiveComparison().isEqualTo(Optional.of(engine));
        assertThat(engine.getId()).isGreaterThan(0);
    }

    @Test
    void whenCreateEngineIdGreaterZeroThenReturnException() {
        var engine = new Engine(1, "engine");
        assertThatThrownBy(() -> engineRepository.create(engine)).isInstanceOf(PersistenceException.class);
    }

    @Test
    void whenFindEngineByIdThenReturnOptionalEngine() {
        var engine = new Engine(0, "engine");
        engineRepository.create(engine);

        var expectedEngine = Optional.of(engine);
        var engineInDb = engineRepository.findEngineById(engine.getId());

        assertThat(engineInDb).usingRecursiveComparison().isEqualTo(expectedEngine);
    }

    @Test
    void whenFindEngineByIdThenReturnOptionalEmpty() {
        var engineInDb = engineRepository.findEngineById(-1);

        assertThat(engineInDb).isEmpty();
    }

    @Test
    void whenUpdateEngineThenReturnActualEngine() {
        var engine = new Engine(0, "engine");
        engineRepository.create(engine);
        engine.setName("newEngine");

        engineRepository.update(engine);

        var engineInDb = engineRepository.findEngineById(engine.getId());

        assertThat(engineInDb).usingRecursiveComparison().isEqualTo(Optional.of(engine));
    }

    @Test
    void whenDeleteEngineByIdThenFindByIdReturnOptionalEmpty() {
        var engine = new Engine(0, "engine");
        engineRepository.create(engine);

        engineRepository.delete(engine.getId());

        var engineInDb = engineRepository.findEngineById(engine.getId());

        assertThat(engineInDb).isEmpty();
    }

    @Test
    void whenFindAllEngineThenReturnEngineCollection() {
        var engine1 = new Engine(0, "engine1");
        engineRepository.create(engine1);
        var engine2 = new Engine(0, "engine2");
        engineRepository.create(engine2);

        var expectedEngines = List.of(engine1, engine2);
        var actualEngines = engineRepository.findAllEngine();

        assertThat(actualEngines).usingRecursiveComparison().isEqualTo(expectedEngines);
    }

    @Test
    void whenFindAllEngineThenReturnEngineCollectionEmpty() {
        var actualEngines = engineRepository.findAllEngine();

        assertThat(actualEngines.isEmpty()).isTrue();
    }
}