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
        return null;
    }

    @Override
    public void delete(Baker baker) {

    }

    @Override
    public void delete(Long bakerId) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Baker> findAll() {

        return new ArrayList<>();
    }

    @Override
    public List<Baker> addAll(Collection<Baker> bakers) {
        System.out.println("HELLO");
        return bakers.stream().toList();
    }
}
