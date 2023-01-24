package io.github.luaprogrammer.api.services;

import io.github.luaprogrammer.api.model.dto.PersonDto;

public interface PersonService {
    PersonDto findById(Long id);
}
