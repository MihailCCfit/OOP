package nsu.fit.tsukanov.pizzeria.modern.modules.baker.repository;

import nsu.fit.tsukanov.pizzeria.modern.modules.baker.Baker;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Repository for bakers.
 */
@Repository
public interface BakerRepository {
    /**
     * Save.
     *
     * @param baker the baker for save
     * @return saved baker
     */
    Baker save(Baker baker);

    void delete(Baker baker);

    void delete(Long bakerId);

    void deleteAll();

    List<Baker> findAll();

    void addAll(Collection<Baker> bakers);
}
