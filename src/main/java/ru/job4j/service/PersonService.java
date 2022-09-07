package ru.job4j.service;

import org.springframework.stereotype.Service;
import ru.job4j.domain.Person;
import ru.job4j.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository store;

    public PersonService(PersonRepository store) {
        this.store = store;
    }

    public Person save(Person person) {
        return store.save(person);
    }

    public Optional<Person> findById(int id) {
        return store.findById(id);
    }

    public Optional<Person> findByUsername(String username) {
        return store.findByUsername(username);
    }

    public List<Person> findAllByRoomId(int id) {
        return store.findAllByRoomId(id);
    }

    public List<Person> findAllByRoomName(String name) {
        return store.findAllByRoomName(name);
    }

    public List<Person> findAll() {
        return store.findAll();
    }

    public void delete(Person person) {
        store.delete(person);
    }
}
