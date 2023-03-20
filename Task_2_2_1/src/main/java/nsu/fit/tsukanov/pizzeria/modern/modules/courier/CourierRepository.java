package nsu.fit.tsukanov.pizzeria.modern.modules.courier;

import java.util.Collection;
import java.util.List;

public interface CourierRepository {
    Courier save(Courier courier);

    void delete(Courier courier);

    void delete(Long courierId);

    void deleteAll();

    List<Courier> findAll();

    void addAll(Collection<Courier> couriers);
}
