package stepan.lys;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import stepan.lys.model.Purchase;
import stepan.lys.repository.PurchaseRepository;

import java.time.LocalDate;

@SpringBootApplication
public class IntelliartsApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntelliartsApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(PurchaseRepository repository) {
        return args -> {
            repository.save(new Purchase(LocalDate.of(2019, 4, 25), 12, "USD","Photo Frame"));
            repository.save(new Purchase(LocalDate.of(2019, 4, 25), 12, "USD","Photo Frame 2"));
            repository.save(new Purchase(LocalDate.of(2018, 4, 25), 24, "EUR","Hat"));
            repository.save(new Purchase(LocalDate.of(2018, 4, 25), 24, "EUR","Hat 2"));
            repository.save(new Purchase(LocalDate.of(2018, 4, 25), 24, "EUR","Hat 3"));
        };
    }

}