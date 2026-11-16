package com.example.person.model;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * REST API for managing Person records.
 *
 *   GET    /api/persons        list all persons
 *   GET    /api/persons/{id}   get one person (404 if missing)
 *   POST   /api/persons        create a person (409 if the id already exists)
 *   PUT    /api/persons/{id}   update name/age (404 if missing)
 *   DELETE /api/persons/{id}   delete a person (404 if missing)
 */
@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private final PersonRepository repository;

    public PersonController(PersonRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Person> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Person findById(@PathVariable String id) {
        return repository.findById(id).orElseThrow(() -> notFound(id));
    }

    @PostMapping
    public ResponseEntity<Person> create(@RequestBody Person person) {
        if (person.getId() == null || person.getId().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id is required");
        }
        if (repository.existsById(person.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Person with id '" + person.getId() + "' already exists");
        }
        Person saved = repository.save(person);
        return ResponseEntity.created(URI.create("/api/persons/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public Person update(@PathVariable String id, @RequestBody Person changes) {
        Person existing = repository.findById(id).orElseThrow(() -> notFound(id));
        existing.setName(changes.getName());
        existing.setAge(changes.getAge());
        return repository.save(existing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!repository.existsById(id)) {
            throw notFound(id);
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private static ResponseStatusException notFound(String id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Person '" + id + "' not found");
    }
}
