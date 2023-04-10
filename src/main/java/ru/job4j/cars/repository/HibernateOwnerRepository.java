package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.dao.Owner;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.0. Проект "АвтоМаг"
 * HibernateOwnerRepository хранилище модели Owner(владельцы авто)
 * CRUD операции
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 01.04.2023
 */
@Repository
@AllArgsConstructor
public class HibernateOwnerRepository implements OwnerRepository {
    private final CrudRepository crudRepository;

    @Override
    public Owner create(Owner owner) {
        crudRepository.run(session -> session.persist(owner));
        return owner;
    }

    @Override
    public Optional<Owner> findOwnerById(int ownerId) {
        return crudRepository.optional(
                """
                        FROM Owner AS o 
                        JOIN FETCH o.user AS u 
                        WHERE o.id =:ownerId
                        """,
                Owner.class,
                Map.of("ownerId", ownerId)
        );
    }

    @Override
    public void update(Owner owner) {
        crudRepository.run(session -> session.merge(owner));
    }

    @Override
    public void delete(int ownerId) {
        crudRepository.run(
                """
                        DELETE FROM Owner AS o 
                        WHERE o.id =:ownerId
                        """,
                Map.of("ownerId", ownerId)
        );
    }

    @Override
    public List<Owner> findAllOwner() {
        return crudRepository.query(
                """
                        FROM Owner AS o 
                        JOIN FETCH o.user AS u 
                        ORDER BY o.id ASC
                        """,
                Owner.class
        );
    }
}
