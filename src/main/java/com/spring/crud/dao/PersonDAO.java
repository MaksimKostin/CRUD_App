package com.spring.crud.dao;

import com.spring.crud.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index(){
        // Исполняем SQL запрос, затем каждую строку таблицы преобразуем в наш класс Person
        // через готовый RowMapper, который самостоятельно назначает
        // значения полей(тк. поля класса и имена столбцов совпадают).
        // И возвращаем список из наших объектов.
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id){
        // Делаем то же самое, что и в методе index,
        // но извлекаем единственный объект из списка при помощи потока.
        // Если используем метод "query" нашего jdbcTemplate, то id передаем как объект.
        return jdbcTemplate.queryForObject("SELECT * FROM Person WHERE id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class));
    }

    public void save(Person person){
        // Используем метод "update" тк запрос ничего не вернёт.
        jdbcTemplate.update("INSERT INTO Person VALUES(DEFAULT, ?, ?, ?, ?, ?)",
                person.getName(),person.getSurname(),
                person.getLastname(), person.getAge(), person.getEmail());
    }

    public void update(int id, Person updatedPerson){
        jdbcTemplate.update("UPDATE Person SET name=?, surname=?, lastname=?, age=?, email=? WHERE id=?",
                updatedPerson.getName(), updatedPerson.getSurname(),
                updatedPerson.getLastname(), updatedPerson.getAge(),
                updatedPerson.getEmail(), id);

    }

    public void delete(int id) {
        // Если используем метод "update" нашего jdbcTemplate, то id передаем как обычную переменную.
       jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }
}
