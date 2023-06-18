package ru.doreamon08.SpringMVCTutor2.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.doreamon08.SpringMVCTutor2.models.Book;
import ru.doreamon08.SpringMVCTutor2.models.Person;

import java.util.List;

@Component
public class BookDao {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM book",
                new BookMapper());
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE book_id=?",
                        new Object[]{id},
                        new BookMapper())
                        .stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(name, author, year) VALUES(?, ?, ?)",
                book.getName(),
                book.getAuthor(),
                book.getYear());
    }

    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE Book SET name=?, author=?, year=? WHERE Book_id=?",
                updatedBook.getName(),
                updatedBook.getAuthor(),
                updatedBook.getYear(),
                id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE Book_id=?", id);
    }

    public void free(int id) {
        jdbcTemplate.update("UPDATE Book SET person_id=null WHERE Book_id=?", id);
    }

    public void assign(int book_id, Person person) {
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE Book_id=?",
                person.getId(),
                book_id);
    }
}
