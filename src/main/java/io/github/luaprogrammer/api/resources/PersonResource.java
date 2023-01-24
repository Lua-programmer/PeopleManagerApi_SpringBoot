package io.github.luaprogrammer.api.resources;

import io.github.luaprogrammer.api.model.dto.PersonDto;
import io.github.luaprogrammer.api.services.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
