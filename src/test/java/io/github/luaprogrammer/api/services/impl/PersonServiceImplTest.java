package io.github.luaprogrammer.api.services.impl;

import io.github.luaprogrammer.api.model.Address;
import io.github.luaprogrammer.api.model.Person;
import io.github.luaprogrammer.api.model.dto.AddressDto;
import io.github.luaprogrammer.api.model.dto.PersonDto;
import io.github.luaprogrammer.api.repository.AddressRepository;
import io.github.luaprogrammer.api.services.exceptions.ObjectNotFoundException;
import io.github.luaprogrammer.api.services.exceptions.RuleBusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonServiceImplTest {

    public static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";
    @Autowired
    private PersonServiceImpl service;
    private PersonDto personDto;
    private Person person;
    private AddressDto addressDto, a1, a2, a3, a4, a5, a6;
    private Address address;
    @Autowired
    private AddressRepository addressRepository;

    @BeforeEach
    void setUp() {
        startPersonAndAddress();
    }

    @Test
    void whenFindByIdThenReturnAnPersonInstance() {
        PersonDto response = service.findById(1L);

        assertEquals(PersonDto.class, response.getClass());
    }

    @Test
    void whenFindByIdIdThenReturnExceptionIfUserNotFound() {
        try {
            service.findById(8L);
        } catch (Exception e) {
            assertEquals(ObjectNotFoundException.class, e.getClass());
            assertEquals(OBJETO_NAO_ENCONTRADO, e.getMessage());
        }
    }

    @Test
    void whenFindAllThenReturnAnListOfUsers() {
        List<PersonDto> response = service.findAll();

        assertNotNull(response);
        assertEquals(response.size(), 1);
        assertEquals(PersonDto.class, response.get(0).getClass());
    }

    @Test
    void whenCreateThenReturnSuccess() {
        PersonDto response = service.create(personDto);

        assertNotNull(response);
        assertEquals(PersonDto.class, response.getClass());
        assertEquals(response.getId(), 3);
        assertEquals("Zoe", response.getName());
        assertEquals(LocalDate.of(2018, 8, 16), response.getBirthDate());
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        PersonDto response = service.update(person.getId(), personDto);
        assertNotNull(response);
        assertEquals(PersonDto.class, response.getClass());
        assertEquals(1, response.getId());
        assertEquals("Zoe", response.getName());
        assertEquals(LocalDate.of(2018, 8, 16), response.getBirthDate());
    }

    @Test
    void deleteWithSuccess() {
        service.delete(1L);

        List<PersonDto> response = service.findAll();
        assertEquals(1, response.size());
    }

    @Test
    void deleteWithObjectNotFoundException() {
        try {
            service.delete(8L);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJETO_NAO_ENCONTRADO, ex.getMessage());
        }
    }

    @Test
    void whenAddAddressToPersonThenReturnRuleBusinessExceptionForNumberHouseEquals() {
        try {
            service.addAddressToPerson(1L, addressDto);
        } catch (Exception e) {
            addressDto.setNumber(689L);
            assertEquals(RuleBusinessException.class, e.getClass());
            assertEquals("O número da casa e o logradouro já existe no sistema para essa pessoa.", e.getMessage());
        }
    }

    @Test
    void whenAddAddressToPersonThenReturnRuleBusinessExceptionForListAddressSizeEquals5() {
        try {
            service.addAddressToPerson(1L, a5);
        } catch (Exception e) {
            personDto.setAddresses(List.of(a1, a2, a3, a4));
            assertEquals(RuleBusinessException.class, e.getClass());
            assertEquals("Você já preencheu o número máximo de endereços.", e.getMessage());
        }
    }

    @Test
    void whenFindAllAddressesToPersonThenReturnSuccess() {
        service.findAllAddressesToPerson(2L);
        personDto.setAddresses(List.of(addressDto, addressDto));

        assertEquals(personDto.getAddresses().size(), 2);
    }

    private void startPersonAndAddress() {
        personDto = new PersonDto(1L, "Zoe", LocalDate.of(2018, 8, 16), LocalDateTime.now(), null);
        person = new Person(1L, "Zoe", LocalDate.of(2018, 8, 16), LocalDateTime.now(), null);
        addressDto = new AddressDto(null, "58115-616", "Rua Joaquim Saraiva", "João Pessoa", 68L, true, person.getId());
        address = new Address(null, "58115-616", "Rua Joaquim Saraiva", "João Pessoa", 689L, true, person);

        a1 = new AddressDto(null, "58215-616", "Rua João Azevedo", "João Pessoa", 60L, true, person.getId());
        a2 = new AddressDto(null, "58335-616", "Rua Maria Bezerra", "João Pessoa", 90L, true, person.getId());
        a3 = new AddressDto(null, "58125-616", "Rua Severina Maria", "João Pessoa", 70L, true, person.getId());
        a4 = new AddressDto(null, "58915-616", "Rua Otavio Mesquita", "João Pessoa", 89L, true, person.getId());
        a5 = new AddressDto(null, "58456-616", "Rua João Maria", "João Pessoa", 79L, true, person.getId());
        a6 = new AddressDto(null, "58123-616", "Rua Antonio Marcos", "João Pessoa", 65L, true, person.getId());
    }
}