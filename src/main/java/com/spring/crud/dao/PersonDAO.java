package com.spring.crud.dao;

import com.spring.crud.models.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;
    private List<Person> people;

    {
        people = new ArrayList<>();

        people.add(new Person(++PEOPLE_COUNT, "Max", 18, "kvarishkin@mail.ru"));
        people.add(new Person(++PEOPLE_COUNT, "Tom", 24, "tom_1998@mail.ru"));
        people.add(new Person(++PEOPLE_COUNT, "Lena", 30, "lena_1991@mail.ru"));
        people.add(new Person(++PEOPLE_COUNT, "Katy", 18, "katy@mail.ru"));
    }

    public List<Person> index(){ return people; }

    public Person show(int id){
        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }

    public void save(Person person){
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }

    public void update(int id, Person updatedPerson){
        Person personToBeUpdated = show(id);

        personToBeUpdated.setName(updatedPerson.getName());
        personToBeUpdated.setAge(updatedPerson.getAge());
        personToBeUpdated.setEmail(updatedPerson.getEmail());
    }

    public void delete(int id) {
        people.removeIf(person -> person.getId() == id);
    }
}
