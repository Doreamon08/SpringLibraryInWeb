package ru.doreamon08.SpringMVCTutor2.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
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
//    private static final String URL = "jdbc:postgresql://localhost:5432/first_db";
//    private static final String USERNAME = "postgres";
//    private static final String PASSWORD = "22081995";
//
//    private static Connection connection;
//
//    static {
//        try {
//            Class.forName("org.postgresql.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }

    public List<Person>  index() {
//        List<Person> people = new ArrayList<>();
//
//        try {
//            Statement statement = connection.createStatement();
//            String SQL = "SELECT * FROM Person";
//            ResultSet resultSet = statement.executeQuery(SQL);
//
//            while(resultSet.next()) {
//                Person person = new Person();
//
//                person.setId(resultSet.getInt("id"));
//                person.setName(resultSet.getString("name"));
//                person.setEmail(resultSet.getString("email"));
//                person.setAge(resultSet.getInt("age"));
//
//                people.add(person);
//            }
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return people;
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
//        Person person = null;
//        try {
//            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Person WHERE id=?");
//            statement.setInt(1, id);
//            ResultSet resultSet = statement.executeQuery();
//
//            resultSet.next();
//
//            person = new Person(resultSet.getInt("id"),
//                                        resultSet.getString("name"),
//                                        resultSet.getInt("age"),
//                                        resultSet.getString("email"));
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        return person;
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public Optional<Person> show(String email) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE email=?",
                new Object[]{email},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void save(Person person) {
//        try {
//            PreparedStatement statement = connection.prepareStatement( "INSERT INTO Person VALUES(1, ?, ?, ?)");
//            statement.setString(1, person.getName());
//            statement.setString(3, person.getEmail());
//            statement.setInt(2, person.getAge());
//
//            statement.executeQuery();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
        jdbcTemplate.update("INSERT INTO Person(name, age, email, address) VALUES(?, ?, ?, ?)",
                                        person.getName(),
                                        person.getAge(),
                                        person.getEmail(),
                                        person.getAddress());
    }

    public void update(int id, Person updatedPerson) {
//        try {
//            PreparedStatement statement =
//                    connection.prepareStatement("UPDATE Person SET name=?, age=?, email=? WHERE id=?");
//            statement.setString(1, updatedPerson.getName());
//            statement.setInt(2, updatedPerson.getAge());
//            statement.setString(3, updatedPerson.getEmail());
//            statement.setInt(4, id);
//
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        jdbcTemplate.update("UPDATE Person SET name=?, age=?, email=?, address=? WHERE id=?",
                                updatedPerson.getName(),
                                updatedPerson.getAge(),
                                updatedPerson.getEmail(),
                                updatedPerson.getAddress(),
                                id);
    }

    public void delete(int id) {
//        try {
//            PreparedStatement statement = connection.prepareStatement("DELETE FROM Person WHERE id=?");
//            statement.setInt(1, id);
//            statement.executeUpdate();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }

    public void testMultipleUpdate() {
        List<Person> people = create1000people();
        long before = System.currentTimeMillis();
        for (Person person : people) {
            jdbcTemplate.update("INSERT INTO Person VALUES(?, ?, ?, ?)",
                    person.getId(),
                    person.getName(),
                    person.getAge(),
                    person.getEmail());
        }

        long after = System.currentTimeMillis();
        System.out.println("Time: " + (after - before));
    }

    public void testBatchUpdate() {
        List<Person> people = create1000people();
        long before = System.currentTimeMillis();
        jdbcTemplate.batchUpdate("INSERT INTO Person VALUES(?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                        preparedStatement.setInt(1, people.get(i).getId());
                        preparedStatement.setString(2, people.get(i).getName());
                        preparedStatement.setInt(3, people.get(i).getAge());
                        preparedStatement.setString(4, people.get(i).getEmail());
                    }

                    @Override
                    public int getBatchSize() {
                        return people.size();
                    }
                });
        long after = System.currentTimeMillis();
        System.out.println("Time: " + (after - before));
    }

    private List<Person> create1000people() {
        List<Person> people = new ArrayList<>();
        for (int i = 0; i < 1000; i++)
            people.add(new Person(i, "Name " + i, 30, "email" + i + "@mail.ru", "address"));
        return people;
    }
}
