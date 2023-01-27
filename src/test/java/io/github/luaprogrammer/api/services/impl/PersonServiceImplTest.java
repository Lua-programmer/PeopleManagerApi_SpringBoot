package io.github.luaprogrammer.api.services.impl;

import io.github.luaprogrammer.api.model.dto.AddressDto;
import io.github.luaprogrammer.api.model.dto.PersonDto;
import io.github.luaprogrammer.api.services.exceptions.ObjectNotFoundException;
import io.github.luaprogrammer.api.services.exceptions.RuleBusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PersonServiceImplTest {

    public static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";
    @Autowired
    private PersonServiceImpl service;
    private PersonDto personDto;
    private AddressDto addressDto;

    @BeforeEach
    void setUp() {
        startPersonAndAddress();
    }

    @Test
    void whenFindByIdThenReturnAnPersonInstance() {
        PersonDto response = service.findById(2L);

        assertEquals(PersonDto.class, response.getClass());
        assertEquals("User", response.getName());
        assertEquals(LocalDate.of(2017,7,17), response.getBirthDate());
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
        assertEquals(1, response.size());
        assertEquals(PersonDto.class, response.get(0).getClass());
    }

    @Test
    void whenCreateThenReturnSuccess() {
        PersonDto response = service.create(personDto);

        assertNotNull(response);
        assertEquals(PersonDto.class, response.getClass());
        assertEquals(3L, response.getId());
        assertEquals("User", response.getName());
        assertEquals(LocalDate.of(2017, 7, 17), response.getBirthDate());
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        PersonDto response = service.update(2L, personDto);
        assertNotNull(response);
        assertEquals(PersonDto.class, response.getClass());
        assertEquals(2L, response.getId());
        assertEquals("User", response.getName());
        assertEquals(LocalDate.of(2017, 7, 17), response.getBirthDate());
    }

    @Test
    void deleteWithSuccess() {
        service.delete(2L);

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
            service.addAddressToPerson(2L, addressDto);
        } catch (Exception e) {
            addressDto.setNumber(689L);
            assertEquals(RuleBusinessException.class, e.getClass());
            assertEquals("O número da casa e o logradouro já existe no sistema para essa pessoa.", e.getMessage());
        }
    }

    @Test
    void whenAddAddressToPersonThenReturnRuleBusinessExceptionForListAddressSizeEquals5() {
        try {
            addressDto.setPersonId(2L);
            addressDto.setNumber(96L);
            service.addAddressToPerson(2L, addressDto);
        } catch (Exception e) {
            assertEquals(RuleBusinessException.class, e.getClass());
            assertEquals("Você já preencheu o número máximo de endereços.", e.getMessage());
        }
    }

    @Test
    void whenFindAllAddressesToPersonThenReturnSuccess() {
        service.findAllAddressesToPerson(1L);
        personDto.setAddresses(List.of(addressDto, addressDto));

        assertEquals(2, personDto.getAddresses().size());
    }

    private void startPersonAndAddress() {
        personDto = new PersonDto(3L, "User", LocalDate.of(2017, 7, 17), LocalDateTime.now(), null);
        addressDto = new AddressDto(null, "58115-616", "Rua Joaquim Saraiva", "João Pessoa", 68L, true, 1L);

    }
}
