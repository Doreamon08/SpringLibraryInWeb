package ru.doreamon08.SpringMVCTutor2.models;

import javax.validation.constraints.*;

public class Person {
    private int id;
    @NotEmpty(message = "Name should not be empty!")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;
    @NotEmpty(message = "Email should not be empty!")
    @Email(message = "Email should be valid")
    private String email;
    @Min(value = 0, message = "Age should be greater than 0")
    private int age;
    //Страна, Город, Индекс (6 цифр)
    @Pattern(regexp = "^[A-Z][a-z]+, [A-Z]{1}[a-z]+, \\d{6}",
            message = "Your address should be in this format: Country, City, Postal Card(6 digits)")
    private String address;

    public Person(int id, String name, int age, String email, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.address = address;
    }

    public Person() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

