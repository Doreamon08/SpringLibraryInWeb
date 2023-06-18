package ru.doreamon08.SpringMVCTutor2.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.doreamon08.SpringMVCTutor2.models.Book;
import ru.doreamon08.SpringMVCTutor2.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person",
                 new PersonMapper());
    }

    public List<Book> indexOfBooks(int person_id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE person_id=?",
                new Object[]{person_id},
                new BookMapper());
    }

    public Person show(int person_id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE person_id=?",
                        new Object[]{person_id},
                new PersonMapper())
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(name, age) VALUES(?, ?)",
                                        person.getName(),
                                        person.getAge());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET name=?, age=? WHERE person_id=?",
                                updatedPerson.getName(),
                                updatedPerson.getAge(),
                                id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE person_id=?", id);
    }
}
