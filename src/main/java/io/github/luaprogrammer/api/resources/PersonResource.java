package io.github.luaprogrammer.api.resources;

import io.github.luaprogrammer.api.model.dto.AddressDto;
import io.github.luaprogrammer.api.model.dto.PersonDto;
import io.github.luaprogrammer.api.services.PersonService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/people")
public class PersonResource {
    public static final String ADD_ADDRESS = "/{id}/add-address";
    public static final String ADDRESSES = "/{id}/addresses";
    private final PersonService service;
    public static final String ID = "/{id}";

    public PersonResource(PersonService service) {
        this.service = service;
    }

    @ApiOperation(value = "Consulta uma pessoa por id")
    @GetMapping(value = ID, produces="application/json")
    public ResponseEntity<PersonDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @ApiOperation(value = "Retorna uma lista de pessoas")
    @GetMapping(produces="application/json")
    public ResponseEntity<List<PersonDto>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @ApiOperation(value = "Cria uma pessoa")
    @PostMapping(produces="application/json", consumes="application/json")
    public ResponseEntity<PersonDto> create(@RequestBody @Valid PersonDto person) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(service.create(person).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @ApiOperation(value = "Edita uma pessoa")
    @PutMapping(value = ID, produces="application/json", consumes="application/json")
    public ResponseEntity<PersonDto> update(@PathVariable Long id, @RequestBody @Valid PersonDto user) {
        service.update(id, user);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Deleta uma pessoa")
    @DeleteMapping(ID)
    public ResponseEntity<PersonDto> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Adiciona endereço para a pessoa")

    @PatchMapping(value = ADD_ADDRESS, produces="application/json", consumes="application/json")
    public ResponseEntity<PersonDto> addAddressToPerson(@PathVariable("id") Long id, @RequestBody @Valid AddressDto address) {
        service.addAddressToPerson(id, address);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Lista os endereços da pessoa")
    @GetMapping( value = ADDRESSES, produces = "application/json")
    public ResponseEntity<List<AddressDto>> findAllAddressToPerson(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findAllAddressesToPerson(id));
    }
}
