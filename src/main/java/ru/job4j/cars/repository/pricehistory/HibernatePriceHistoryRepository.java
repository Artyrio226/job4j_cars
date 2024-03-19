package ru.job4j.cars.repository.pricehistory;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.PriceHistory;
import ru.job4j.cars.repository.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernatePriceHistoryRepository implements PriceHistoryRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<PriceHistory> create(PriceHistory priceHistory) {
        return crudRepository.run(session -> session.persist(priceHistory)) ? Optional.of(priceHistory) : Optional.empty();
    }

    @Override
    public boolean update(PriceHistory priceHistory) {
        return crudRepository.run(session -> session.merge(priceHistory));
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.run(
                "delete from PriceHistory where id = :fId",
                Map.of("fId", id)
        );
    }

    @Override
    public List<PriceHistory> findAllOrderById() {
        return crudRepository.query("from PriceHistory order by id desc", PriceHistory.class);
    }

    @Override
    public Optional<PriceHistory> findById(int id) {
        return crudRepository.optional(
                "from PriceHistory where id = :fId", PriceHistory.class,
                Map.of("fId", id)
        );
    }
}
