package nsu.fit.tsukanov.baker.repository;

import nsu.fit.tsukanov.baker.Baker;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BakerRepository {
    Baker save(Baker baker);

    void delete(Baker baker);

    void delete(Long bakerId);

    void deleteAll();

    List<Baker> findAll();

    List<Baker> addAll(Collection<Baker> bakers);
}
