package com.generation.HelloRESTWorld.model;

import java.time.LocalDate;

public class Student {

    private long id;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;

    public Student(long id, String firstName, String lastName, LocalDate birthdate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setId(long id) {
        this.id = id;
    }
}
