package io.github.luaprogrammer.api.services;

import io.github.luaprogrammer.api.model.dto.PersonDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonService {
    PersonDto findById(Long id);

    Page<PersonDto> findAll(Pageable pageable);

    PersonDto create(PersonDto person);
}
