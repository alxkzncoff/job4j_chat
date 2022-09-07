package ru.job4j.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.domain.Room;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends CrudRepository<Room, Integer> {

    @Override
    List<Room> findAll();

    @Query("SELECT DISTINCT r FROM Room r WHERE r.name=?1")
    Optional<Room> findByName(String name);
}
