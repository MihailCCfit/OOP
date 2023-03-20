package nsu.fit.tsukanov.pizzeria.modern.modules.courier;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nsu.fit.tsukanov.pizzeria.modern.common.configuration.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CourierRepositoryJSON implements CourierRepository {
    private final List<Courier> couriers;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File fileCouriers;

    public CourierRepositoryJSON() {
        fileCouriers = new File(Configuration.COURIERS);
        if (!fileCouriers.exists()) {
            couriers = new ArrayList<>();
            writeToFile(couriers);
        } else {
            try {
                couriers = objectMapper.readValue(fileCouriers, new TypeReference<List<Courier>>() {
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void writeToFile(Object object) {
        try {
            objectMapper.writeValue(fileCouriers, object);
        } catch (IOException e) {
            fileCouriers.mkdirs();
            try {
                fileCouriers.createNewFile();
                objectMapper.writeValue(fileCouriers, object);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public Courier save(Courier courier) {
        couriers.add(courier);
        writeToFile(couriers);
        return courier;
    }

    @Override
    public void delete(Courier courier) {
        couriers.remove(courier);
        writeToFile(couriers);
    }

    @Override
    public void delete(Long courierId) {
        Iterator<Courier> iterator = couriers.iterator();
        while (iterator.hasNext()) {
            var cour = iterator.next();
            if (cour.id().equals(courierId)) {
                iterator.remove();
                break;
            }
        }
        writeToFile(couriers);
    }

    @Override
    public void deleteAll() {
        couriers.clear();
        writeToFile(couriers);
    }

    @Override
    public List<Courier> findAll() {
        return new ArrayList<>(couriers);
    }

    @Override
    public void addAll(Collection<Courier> couriers) {
        this.couriers.addAll(couriers);
        writeToFile(couriers);
    }
    //TODO: CourierRepo implement class
}
