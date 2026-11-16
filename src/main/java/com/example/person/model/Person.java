package com.example.person.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * JPA entity mapped to the "person" table defined in model.sql.
 */
@Entity
@Table(name = "person")
public class Person {

    @Id
    @Column(name = "id", nullable = false, columnDefinition = "TEXT")
    private String id;

    @Column(name = "name", columnDefinition = "TEXT")
    private String name;

    @Column(name = "age")
    private Integer age;

    protected Person() {
        // required by JPA
    }

    public Person(String id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{id='" + id + "', name='" + name + "', age=" + age + "}";
    }
}
