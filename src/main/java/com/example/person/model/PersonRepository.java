package com.example.person.model;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data repository for Person (CRUD, paging and sorting out of the box).
 */
public interface PersonRepository extends JpaRepository<Person, String> {
}
