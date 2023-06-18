package ru.doreamon08.SpringMVCTutor2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.doreamon08.SpringMVCTutor2.dao.BookDao;
import ru.doreamon08.SpringMVCTutor2.dao.PersonDAO;
import ru.doreamon08.SpringMVCTutor2.models.Book;
import ru.doreamon08.SpringMVCTutor2.models.Person;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDao bookDao;
    private final PersonDAO personDAO;
    @Autowired
    public BookController(BookDao bookDao, PersonDAO personDAO) {
        this.bookDao = bookDao;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDao.index());
        return "books/index";
    }

    @GetMapping("{book_id}")
    public String show(@PathVariable("book_id") int book_id, @ModelAttribute("person") Person person,
                       Model model) {
        model.addAttribute("book", bookDao.show(book_id));
        model.addAttribute("person", personDAO.show(bookDao.show(book_id).getPerson_id()));
        model.addAttribute("people", personDAO.index());
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("new_book", new Book());
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("new_book") Book book) {
        bookDao.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{book_id}/edit")
    public String edit(@PathVariable("book_id") int book_id, Model model) {
        model.addAttribute("book", bookDao.show(book_id));
        return "books/edit";
    }

    @PatchMapping("/{book_id}")
    public String update(@ModelAttribute("book") Book book,
                         @PathVariable("book_id") int book_id) {
        bookDao.update(book_id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDao.delete(id);
        return "redirect:/books";
    }
    @PatchMapping("/{book_id}/free")
    public String free(@PathVariable("book_id") int book_id) {
        bookDao.free(book_id);
        return "redirect:/books/{book_id}";
    }

//    @PatchMapping("/{book_id}/assign")
//    public String assign(@PathVariable("book_id") int book_id,
//                         @ModelAttribute("personId") Person person) {
//
//        bookDao.assign(book_id, person);
//
//        return "redirect:/books/{book_id}";
//    }

    @PatchMapping("/{book_id}/assign")
    public String assign(@PathVariable("book_id") int bookId,
                         @RequestParam("personId") int personId) {
        Person person = personDAO.show(personId);

        bookDao.assign(bookId, person);

        return "redirect:/books/{book_id}";
    }

}
