package ru.doreamon08.SpringMVCTutor2.models;

import javax.validation.constraints.*;

public class Person {
    private int person_id;
    @NotEmpty(message = "Name should not be empty!")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 30 characters")
    private String name;
    @Min(value = 0, message = "Age should be greater than 0")
    private int age;

    public Person(int person_id, String name, int age) {
        this.person_id = person_id;
        this.name = name;
        this.age = age;
    }

    public Person() { }

    public int getId() {
        return person_id;
    }

    public void setId(int person_id) {
        this.person_id = person_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

