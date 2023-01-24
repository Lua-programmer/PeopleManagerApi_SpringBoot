package io.github.luaprogrammer.api.services.impl;

import io.github.luaprogrammer.api.model.dto.PersonDto;
import io.github.luaprogrammer.api.repository.PersonRepository;
import io.github.luaprogrammer.api.services.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    private final ModelMapper mapper;
    private final PersonRepository pRepository;

    public PersonServiceImpl(ModelMapper mapper, PersonRepository pRepository) {
        this.mapper = mapper;
        this.pRepository = pRepository;
    }

    @Override
    public PersonDto findById(Long id) {
        return mapper.map(
                pRepository.findById(id),
                PersonDto.class);
    }

    @Override
    public Page<PersonDto> findAll(Pageable pageable) {
        return pRepository.findAll(pageable).map(p -> mapper.map(p, PersonDto.class));
    }
}
