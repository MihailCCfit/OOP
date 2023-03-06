package nsu.fit.tsukanov.baker;

import lombok.extern.slf4j.Slf4j;
import nsu.fit.tsukanov.baker.repository.BakerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
public class InitialBaker {
    @Bean
    CommandLineRunner bakerInitialization(BakerRepository bakerRepository) {
        return args -> {
            List<Baker> bakers = bakerRepository.findAll();
            if (bakers.isEmpty()) {
                bakerRepository.addAll(initializationList());
                log.info("First initialization");
            } else {
                log.info("Downloaded bakers, amount: {}", bakers.size());
            }
            log.info("Persons was initialized");
        };
    }

    private List<Baker> initializationList() {
        List<Baker> bakers = new ArrayList<>();
        bakers.add(new Baker(0L, "f", 1L, 1L));
        return bakers;
    }
}

