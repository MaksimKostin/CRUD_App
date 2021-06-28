package com.spring.crud.controllers;

import com.spring.crud.dao.PersonDAO;
import com.spring.crud.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model){
        // Получим всех людей из DAO и передадим на отображение в представление
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        // Получим одного человека по id из DAO и передадим на отображение в представление
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }

    // Переход на страницу создания нового человека
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("emptyPerson") Person person){
        return "people/new";
    }

    // Метод для сохранения нового человека с пришедшими характеристиками из формы
    @PostMapping()
    public String create(@ModelAttribute("filledPerson") Person person){
        personDAO.save(person);
        return "redirect:/people";
    }

    // Метод для отображения страницы редактирования человека
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("editablePerson", personDAO.show(id));
        return "people/edit";
    }

    // Метод добавляющий изменения существуещего человека
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("updatedPerson") Person person, @PathVariable("id") int id){
        personDAO.update(id, person);
        return "redirect:/people";
    }

    // Метод удаляющий человека из нашей БД
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personDAO.delete(id);
        return "redirect:/people";
    }

}
