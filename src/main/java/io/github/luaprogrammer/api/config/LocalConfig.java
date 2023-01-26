package io.github.luaprogrammer.api.config;

import io.github.luaprogrammer.api.model.Address;
import io.github.luaprogrammer.api.model.Person;
import io.github.luaprogrammer.api.repository.AddressRepository;
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
    private final AddressRepository aRepository;

    public LocalConfig(PersonRepository pRepository, AddressRepository aRepository) {
        this.pRepository = pRepository;
        this.aRepository = aRepository;
    }

    @Bean
    public void startDB() {
        Person p1 = new Person(1L, "Zoe", LocalDate.of(2018, 8, 16), LocalDateTime.now(), null);
        Person p2 = new Person(2L, "Petter", LocalDate.of(1992, 9, 12), LocalDateTime.now(), null);

        Address a1 = new Address(1L, "58115-616", "Rua Joaquim Saraiva", "João Pessoa", 689L, true, p1);
        Address a2 = new Address(2L, "58115-616", "Rua Joaquim Saraiva", "João Pessoa", 689L, true, p2);
        Address a3 = new Address(2L, "58115-616", "Rua Joaquim Saraiva", "João Pessoa", 689L, true, p2);
        Address a4 = new Address(2L, "58115-616", "Rua Joaquim Saraiva", "João Pessoa", 689L, true, p2);
        Address a5 = new Address(2L, "58115-616", "Rua Joaquim Saraiva", "João Pessoa", 689L, true, p2);
        Address a6 = new Address(2L, "58115-616", "Rua Joaquim Saraiva", "João Pessoa", 689L, true, p2);

        pRepository.saveAll(List.of(p1, p2));
        aRepository.saveAll(List.of(a1, a2, a3, a4, a5, a6));
    }
}