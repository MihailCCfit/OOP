package nsu.fit.tsukanov.pizzeria.modern.persons.baker;

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
    BakerEntity save(BakerEntity baker);

    void delete(BakerEntity baker);

    void delete(Long bakerId);

    void deleteAll();

    List<BakerEntity> findAll();

    void saveAll(Collection<BakerEntity> bakers);
}
