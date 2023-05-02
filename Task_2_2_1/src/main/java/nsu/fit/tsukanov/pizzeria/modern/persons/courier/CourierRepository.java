package nsu.fit.tsukanov.pizzeria.modern.persons.courier;

import java.util.Collection;
import java.util.List;

public interface CourierRepository {
    Courier save(Courier courier);

    void delete(Courier courier);

    void delete(Long courierId);

    void deleteAll();

    List<Courier> findAll();

    void saveAll(Collection<Courier> couriers);
}
