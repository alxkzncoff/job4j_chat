package ru.job4j.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.domain.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {

    @Query("SELECT DISTINCT p FROM Person p WHERE p.username = ?1")
    Optional<Person> findByUsername(String username);

    @Query("SELECT DISTINCT p FROM Person p JOIN FETCH p.room r WHERE r.name = ?1")
    List<Person> findAllByRoomName(String name);

    @Query("SELECT DISTINCT p FROM Person p JOIN FETCH p.room r WHERE r.id = ?1")
    List<Person> findAllByRoomId(int id);

    @Override
    List<Person> findAll();
}
