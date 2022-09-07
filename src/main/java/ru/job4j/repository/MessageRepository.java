package ru.job4j.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.domain.Message;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {

    @Query("SELECT DISTINCT m FROM Message m "
            + "JOIN FETCH m.person p "
            + "JOIN FETCH m.room r WHERE r.id = ?1 ORDER BY m.created")
    List<Message> findAllByRoomId(int id);

    @Query("SELECT DISTINCT m FROM Message m "
            + "JOIN FETCH m.person p "
            + "JOIN FETCH m.room r WHERE r.name = ?1 ORDER BY m.created")
    List<Message> findAllByRoomName(String name);


    @Override
    List<Message> findAll();
}
