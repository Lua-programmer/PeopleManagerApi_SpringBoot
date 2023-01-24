package io.github.luaprogrammer.api.config;

import io.github.luaprogrammer.api.model.Person;
import io.github.luaprogrammer.api.repository.PersonRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    private final PersonRepository pRepository;

    public LocalConfig(PersonRepository pRepository) {
        this.pRepository = pRepository;
    }


    @Bean
    public void startDB() {
        Person p1 = new Person(1L, "Zoe", LocalDate.of(2018, 8, 16), LocalDateTime.now(), null);
        Person p2 = new Person(2L, "Petter", LocalDate.of(1992, 9, 12), LocalDateTime.now(), null);

        pRepository.saveAll(List.of(p1, p2));
    }
}