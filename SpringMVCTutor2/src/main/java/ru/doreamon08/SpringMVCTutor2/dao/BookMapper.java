package ru.doreamon08.SpringMVCTutor2.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.doreamon08.SpringMVCTutor2.models.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        Book book = new Book(resultSet.getInt("book_id"),
                resultSet.getInt("person_id"),
                resultSet.getString("name"),
                resultSet.getString("author"),
                resultSet.getInt("year"));
        return book;
    }
}
