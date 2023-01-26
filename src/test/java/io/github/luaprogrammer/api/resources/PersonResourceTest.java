package io.github.luaprogrammer.api.resources;

import io.github.luaprogrammer.api.model.dto.AddressDto;
import io.github.luaprogrammer.api.model.dto.PersonDto;
import io.github.luaprogrammer.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PersonResourceTest {

    @Autowired
    private PersonResource resource;

    private PersonDto personDto;
    private AddressDto addressDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startPersonAndAddress();
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
        ResponseEntity<PersonDto> response = resource.findById(2L);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(PersonDto.class, response.getBody().getClass());
    }

    @Test
    void whenFindByIdThenReturnObjectNotFound() {

        try {
            ResponseEntity<PersonDto> response = resource.findById(1L);
        } catch (Exception e) {
            assertEquals(ObjectNotFoundException.class, e.getClass());
            assertEquals("Objeto não encontrado", e.getMessage());
        }
    }

    @Test
    void whenFindAllThenReturnSucess() {
        ResponseEntity<List<PersonDto>> response = resource.findAll();

        assertEquals(1, response.getBody().size());
    }

    @Test
    void whenCreateThenReturnSucess() {
        ResponseEntity<PersonDto> response = resource.create(personDto);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
    }

    @Test
    void whenUpdateThenReturnSucess() {
        ResponseEntity<PersonDto> response = resource.update(2L, personDto);

        assertEquals(ResponseEntity.class, response.getClass());
    }

    @Test
    void whenDeleteThenReturnSucess() {
        resource.delete(1L);
        ResponseEntity<List<PersonDto>> response = resource.findAll();

        assertEquals(1, response.getBody().size());
    }

    @Test
    void whenDeleteThenReturnObjectNotFound() {
        try {
            resource.delete(9L);
        } catch (Exception e) {
            assertEquals(ObjectNotFoundException.class, e.getClass());
            assertEquals("Objeto não encontrado", e.getMessage());
        }
    }

    @Test
    void whenAddAddressToPersonThenReturnSucess() {
        resource.addAddressToPerson(1L, addressDto);

        assertEquals(1L, addressDto.getPersonId());
    }

    @Test
    void findAllAddressToPerson() {
        ResponseEntity<List<AddressDto>> response = resource.findAllAddressToPerson(2L);

        assertEquals(1, response.getBody().size());
    }

    private void startPersonAndAddress() {
        personDto = new PersonDto(null, "Zoe", LocalDate.of(2018, 8, 16), LocalDateTime.now(), null);
       addressDto = new AddressDto(null, "58115-616", "Rua Joaquim Saraiva", "João Pessoa", 68L, true, 1L);
    }
}