package io.github.luaprogrammer.api.resources;

import io.github.luaprogrammer.api.model.dto.PersonDto;
import io.github.luaprogrammer.api.services.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/people")
public class PersonResource {
    private final PersonService service;
    public static final String ID = "/{id}";

    public PersonResource(PersonService service) {
        this.service = service;
    }

    @GetMapping(ID)
    public ResponseEntity<PersonDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<PersonDto>> findAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok().body(service.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<PersonDto> create(@RequestBody PersonDto person) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(service.create(person).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(ID)
    public ResponseEntity<PersonDto> update(@PathVariable Long id, @RequestBody PersonDto user) {
        service.update(id, user);
        return ResponseEntity.noContent().build();
    }
}
