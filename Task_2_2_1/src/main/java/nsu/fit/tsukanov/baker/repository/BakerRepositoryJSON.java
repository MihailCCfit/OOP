package nsu.fit.tsukanov.baker.repository;

import nsu.fit.tsukanov.baker.Baker;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class BakerRepositoryJSON implements BakerRepository {
    @Override
    public Baker save(Baker baker) {
        //TODO: saving in JSON
        return null;
    }

    @Override
    public void delete(Baker baker) {
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
        return new ArrayList<>();
    }

    @Override
    public List<Baker> addAll(Collection<Baker> bakers) {
        System.out.println("HELLO");
        //TODO: add all to JSON
        return bakers.stream().toList();
    }
}
