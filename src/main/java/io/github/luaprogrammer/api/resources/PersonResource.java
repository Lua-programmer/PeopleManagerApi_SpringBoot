package io.github.luaprogrammer.api.resources;

import io.github.luaprogrammer.api.model.dto.PersonDto;
import io.github.luaprogrammer.api.services.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
