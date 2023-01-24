package io.github.luaprogrammer.api.services;

import io.github.luaprogrammer.api.model.dto.AddressDto;
import io.github.luaprogrammer.api.model.dto.PersonDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonService {
    PersonDto findById(Long id);

    Page<PersonDto> findAll(Pageable pageable);

    PersonDto create(PersonDto person);

    PersonDto update(Long id, PersonDto person);

    void delete(Long id);

    PersonDto addAddressToPerson(Long id, AddressDto address);
}
