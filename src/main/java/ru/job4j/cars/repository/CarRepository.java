package ru.job4j.cars.repository;

import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.0. Проект "АвтоМаг"
 * CarRepository интерфейс описывает поведение хранилища модели Car.
 * CRUDA операции.
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 01.04.2023
 */
public interface CarRepository {
    Car create(Car car);

    Optional<Car> findCarById(int carId);

    void update(Car car);

    void delete(int carId);

    List<Car> findAllCar();
}
