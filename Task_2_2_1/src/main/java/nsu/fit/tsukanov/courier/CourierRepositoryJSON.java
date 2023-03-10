package nsu.fit.tsukanov.courier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CourierRepositoryJSON implements CourierRepository {
    private final List<Courier> couriers = new ArrayList<>();

    @Override
    public Courier save(Courier courier) {
        couriers.add(courier);
        return courier;
    }

    @Override
    public void delete(Courier courier) {
        couriers.remove(courier);
    }

    @Override
    public void delete(Long courierId) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Courier> findAll() {
        return new ArrayList<>(couriers);
    }

    @Override
    public void addAll(Collection<Courier> couriers) {
        this.couriers.addAll(couriers);
    }
    //TODO: CourierRepo implement class
}
