package ru.job4j.service;

import org.springframework.stereotype.Service;
import ru.job4j.domain.Room;
import ru.job4j.repository.RoomRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository store;

    public RoomService(RoomRepository store) {
        this.store = store;
    }

    public Room save(Room room) {
        return store.save(room);
    }

    public Optional<Room> findById(int id) {
        return store.findById(id);
    }

    public Optional<Room> findByName(String name) {
        return store.findByName(name);
    }

    public List<Room> findAll() {
        return store.findAll();
    }

    public void delete(Room room) {
        store.delete(room);
    }
}
