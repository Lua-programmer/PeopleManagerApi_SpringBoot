package io.github.luaprogrammer.api.services.impl;

import io.github.luaprogrammer.api.model.Address;
import io.github.luaprogrammer.api.model.Person;
import io.github.luaprogrammer.api.model.dto.AddressDto;
import io.github.luaprogrammer.api.model.dto.PersonDto;
import io.github.luaprogrammer.api.repository.AddressRepository;
import io.github.luaprogrammer.api.repository.PersonRepository;
import io.github.luaprogrammer.api.services.PersonService;
import io.github.luaprogrammer.api.services.exceptions.DataIntegrityViolationException;
import io.github.luaprogrammer.api.services.exceptions.ObjectNotFoundException;
import io.github.luaprogrammer.api.services.exceptions.RuleBusinessException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    private final ModelMapper mapper;
    private final PersonRepository pRepository;
    private final AddressRepository aRepository;

    public PersonServiceImpl(ModelMapper mapper, PersonRepository pRepository, AddressRepository aRepository) {
        this.mapper = mapper;
        this.pRepository = pRepository;
        this.aRepository = aRepository;
    }

    @Override
    public PersonDto findById(Long id) {
        return mapper.map(
                pRepository.findById(id).orElseThrow(
                        () -> new ObjectNotFoundException("Objeto não encontrado")
                ),
                PersonDto.class);
    }

    @Override
    public List<PersonDto> findAll() {
        return pRepository.findAll().stream().map(p -> mapper.map(p, PersonDto.class)).collect(Collectors.toList());
    }

    @Override
    public PersonDto create(PersonDto person) {
        return mapper.map(
                pRepository.save(mapper.map(person, Person.class)),
                PersonDto.class);
    }

    @Override
    public PersonDto update(Long id, PersonDto person) {
        pRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
        person.setId(id);
        return mapper.map(
                pRepository.save(mapper.map(person, Person.class)), PersonDto.class);
    }

    @Override
    public void delete(Long id) {
        Person person = pRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Objeto não encontrado")
        );
        pRepository.delete(person);
    }

    @Override
    public PersonDto addAddressToPerson(Long id, AddressDto address) {
        Person person = pRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Objeto não encontrado")
        );

        Address addressSaved = aRepository.save(mapper.map(address, Address.class));

        addressSaved.setPerson(Person.builder().id(id).build());

        if (person.getAddresses().size() <= 4) {
            if (person.getAddresses().isEmpty()) {
                addressSaved.setIsPrincipal(true);
            }
            for (int i = 0; i < person.getAddresses().size(); i++) {
                if (person.getAddresses().get(i).getIsPrincipal() && addressSaved.getIsPrincipal()) {
                    person.getAddresses().get(i).setIsPrincipal(false);
                }
                if (person.getAddresses().get(i).getPlace().equals(addressSaved.getPlace())
                        && person.getAddresses().get(i).getNumber().equals(addressSaved.getNumber())) {
                    throw new RuleBusinessException("O número da casa e o logradouro já existe no sistema para essa pessoa.");
                }
            }
        } else {
            throw new RuleBusinessException("Você já preencheu o número máximo de endereços.");
        }

        person.getAddresses().add(addressSaved);

        Person personSaved = pRepository.save(person);
        return mapper.map(personSaved, PersonDto.class);
    }

    @Override
    public List<AddressDto> findAllAddressesToPerson(Long id) {
        pRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
        List<Address> addresses = aRepository.findAddressByPersonId(id);
        return addresses.stream().map(a -> mapper.map(a, AddressDto.class)).collect(Collectors.toList());
    }
}
