package ru.doreamon08.SpringMVCTutor2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.doreamon08.SpringMVCTutor2.dao.BookDao;
import ru.doreamon08.SpringMVCTutor2.dao.PersonDAO;
import ru.doreamon08.SpringMVCTutor2.models.Person;
import ru.doreamon08.SpringMVCTutor2.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;
    private final BookDao bookDao;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonDAO personDAO, BookDao bookDao, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.bookDao = bookDao;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("{person_id}")
    public String show(@PathVariable("person_id") int person_id, Model model) {
        model.addAttribute("person", personDAO.show(person_id));
        model.addAttribute("books", personDAO.indexOfBooks(person_id));
        return "people/show";
    }

    @GetMapping ("/new")//сайт
    public String newPerson(Model model) {
        model.addAttribute("person", new Person());
        return "people/new";//где лежит наша форма html
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {

        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "people/new";

        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public  String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "people/edit";

        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }


}
