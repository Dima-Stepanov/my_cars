package ru.job4j.cars.repository;

import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.0. Проект "АвтоМаг"
 * EngineRepository интерфейс описывает поведение хранилища модели Engine.
 * CRUD операции.
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 01.04.2023
 */
public interface EngineRepository {
    Engine create(Engine engine);

    Optional<Engine> findEngineById(int engineId);

    void update(Engine engine);

    void delete(int engineId);

    List<Engine> findAllEngine();
}
