package nsu.fit.tsukanov.pizzeria.modern.persons.courier;

import java.util.Collection;
import java.util.List;

public interface CourierRepository {
    CourierEntity save(CourierEntity courier);

    void delete(CourierEntity courier);

    void delete(Long courierId);

    void deleteAll();

    List<CourierEntity> findAll();

    void addAll(Collection<CourierEntity> couriers);
}
