package nsu.fit.tsukanov.baker.repository;

import nsu.fit.tsukanov.baker.Baker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BakerRepositoryJSON implements BakerRepository {
    private final List<Baker> bakers = new ArrayList<>();

    @Override
    public Baker save(Baker baker) {
        bakers.add(baker);
        //TODO: saving in JSON
        return baker;
    }

    @Override
    public void delete(Baker baker) {
        bakers.remove(baker);
        //TODO: deleting from JSON
    }

    @Override
    public void delete(Long bakerId) {
        //TODO: deleting from JSON
    }

    @Override
    public void deleteAll() {
        //TODO: deleting from JSON
    }

    @Override
    public List<Baker> findAll() {
        //TODO: finding all from JSON
        return new ArrayList<>(bakers);
    }

    @Override
    public void addAll(Collection<Baker> bakers) {
        this.bakers.addAll(bakers);
    }
}
