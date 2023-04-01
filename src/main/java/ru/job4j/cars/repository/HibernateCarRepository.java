package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.0. Проект "АвтоМаг"
 * HibernateCarRepository хранилище модели Car (Продаваемый автомобиль)
 * CRUD операции
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 01.04.2023
 */
@Repository
@AllArgsConstructor
public class HibernateCarRepository implements CarRepository {
    private final CrudRepository crudRepository;

    @Override
    public Car create(Car car) {
        crudRepository.run(session -> session.persist(car));
        return car;
    }

    @Override
    public Optional<Car> findCarById(int carId) {
        return crudRepository.optional(
                """
                        FROM Car AS c 
                        JOIN FETCH c.engine AS e 
                        JOIN FETCH c.owners AS o 
                        JOIN FETCH o.user AS u  
                        WHERE c.id =:carId 
                        """,
                Car.class,
                Map.of("carId", carId)
        );
    }

    @Override
    public void update(Car car) {
        crudRepository.run(session -> session.merge(car));
    }

    @Override
    public void delete(int carId) {
        crudRepository.run(
                """
                        DELETE FROM Car AS c 
                        WHERE c.id =:carId
                        """,
                Map.of("carId", carId)
        );
    }

    @Override
    public List<Car> findAllCar() {
        return crudRepository.query(
                """
                        FROM Car AS c 
                        JOIN FETCH c.engine AS e 
                        JOIN FETCH c.owners AS o
                        JOIN FETCH o.user AS u 
                        ORDER BY c.id ASC
                        """,
                Car.class
        );
    }
}
