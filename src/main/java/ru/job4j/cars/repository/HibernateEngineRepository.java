package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.dao.Engine;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.0. Проект "АвтоМаг"
 * HibernateEngineRepository хранилище модели Engine.
 * CRUD операции
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 01.04.2023
 */
@Repository
@AllArgsConstructor
public class HibernateEngineRepository implements EngineRepository {
    private final CrudRepository crudRepository;

    @Override
    public Engine create(Engine engine) {
        crudRepository.run(session -> session.persist(engine));
        return engine;
    }

    @Override
    public Optional<Engine> findEngineById(int engineId) {
        return crudRepository.optional(
                """
                        FROM Engine AS e 
                        WHERE e.id =:engineId
                        """,
                Engine.class,
                Map.of("engineId", engineId)
        );
    }

    @Override
    public void update(Engine engine) {
        crudRepository.run(session -> session.merge(engine));
    }

    @Override
    public void delete(int engineId) {
        crudRepository.run(
                """
                        DELETE FROM Engine AS e 
                        WHERE e.id =:engineId
                        """,
                Map.of("engineId", engineId)
        );
    }

    @Override
    public List<Engine> findAllEngine() {
        return crudRepository.query(
                """
                        FROM Engine AS e ORDER BY e.id ASC
                        """,
                Engine.class
        );
    }
}
