package nsu.fit.tsukanov;

import nsu.fit.tsukanov.baker.BakerService;
import nsu.fit.tsukanov.client.ClientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Initial {

    @Bean
    CommandLineRunner bakerInitialization(BakerService bakerService, ClientService clientService) {
        return args -> {
            bakerService.startWorking();
            clientService.startWorking();
        };
    }

}
