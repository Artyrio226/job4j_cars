package ru.job4j.cars.repository.owner;

import ru.job4j.cars.model.Owner;

import java.util.List;
import java.util.Optional;

public interface OwnerRepository {
    Optional<Owner> create(Owner owner);
    boolean update(Owner owner);
    boolean delete(Long id);
    List<Owner> findAllOrderById();
    Optional<Owner> findById(Long id);
    List<Owner> findByLikeName(String name);
    Optional<Owner> findByName(String name);
}
