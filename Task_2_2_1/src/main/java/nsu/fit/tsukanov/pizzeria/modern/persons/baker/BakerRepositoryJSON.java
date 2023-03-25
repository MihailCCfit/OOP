package nsu.fit.tsukanov.pizzeria.modern.persons.baker;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nsu.fit.tsukanov.pizzeria.modern.common.configuration.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class BakerRepositoryJSON implements BakerRepository {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File fileBakers;

    private List<BakerEntity> bakers;

    public BakerRepositoryJSON() {
        fileBakers = new File(Configuration.BAKERS);
        if (!fileBakers.exists()) {
            bakers = new ArrayList<>();
            writeToFile(bakers);
        } else {
            try {
                bakers = objectMapper.readValue(fileBakers, new TypeReference<List<BakerEntity>>() {
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }

    private void writeToFile(Object object) {
        try {
            objectMapper.writeValue(fileBakers, object);
        } catch (IOException e) {
            fileBakers.mkdirs();
            try {
                fileBakers.createNewFile();
                objectMapper.writeValue(fileBakers, object);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public BakerEntity save(BakerEntity baker) {
        bakers.add(baker);
        writeToFile(bakers);

        return baker;
    }

    @Override
    public void delete(BakerEntity baker) {
        bakers.remove(baker);
        writeToFile(bakers);
    }

    @Override
    public void delete(Long bakerId) {
        bakers = bakers.stream().filter(baker -> !baker.id().equals(bakerId)).collect(Collectors.toList());
        writeToFile(bakers);
    }

    @Override
    public void deleteAll() {
        bakers.clear();
        writeToFile(bakers);
    }

    @Override
    public List<BakerEntity> findAll() {
        return new ArrayList<>(bakers);
    }

    @Override
    public void addAll(Collection<BakerEntity> bakers) {
        this.bakers.addAll(bakers);
        writeToFile(bakers);
    }
}
