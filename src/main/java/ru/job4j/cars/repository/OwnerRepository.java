package ru.job4j.cars.repository;

import ru.job4j.cars.model.dao.Owner;

import java.util.List;
import java.util.Optional;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.0. Проект "АвтоМаг"
 * OwnerRepository интерфейс описывает поведение хранилища модели Owner.
 * CRUD операции.
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 01.04.2023
 */
public interface OwnerRepository {
    Owner create(Owner owner);

    Optional<Owner> findOwnerById(int ownerId);

    void update(Owner owner);

    void delete(int ownerId);

    List<Owner> findAllOwner();
}
