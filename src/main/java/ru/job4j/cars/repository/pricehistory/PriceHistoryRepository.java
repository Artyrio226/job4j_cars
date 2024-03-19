package ru.job4j.cars.repository.pricehistory;

import ru.job4j.cars.model.PriceHistory;

import java.util.List;
import java.util.Optional;

public interface PriceHistoryRepository {
    Optional<PriceHistory> create(PriceHistory priceHistory);
    boolean update(PriceHistory priceHistory);
    boolean delete(int id);
    List<PriceHistory> findAllOrderById();
    Optional<PriceHistory> findById(int id);
}
