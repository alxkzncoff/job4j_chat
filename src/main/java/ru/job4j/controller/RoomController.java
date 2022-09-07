package ru.job4j.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.domain.Room;
import ru.job4j.service.PersonService;
import ru.job4j.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;
    private final PersonService personService;

    public RoomController(RoomService roomService, PersonService personService) {
        this.roomService = roomService;
        this.personService = personService;
    }

    @GetMapping("/all")
    public List<Room> findAll() {
        return roomService.findAll();
    }

    @PostMapping("/")
    public ResponseEntity<Room> create(@RequestBody Room room) {
        room.setOwner(personService
                .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .get()
                .getUsername()
        );
        return new ResponseEntity<>(
                roomService.save(room),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Room room) {
        room.setOwner(personService
                .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .get()
                .getUsername()
        );
        roomService.save(room);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Room> findById(@PathVariable int id) {
        var room = roomService.findById(id);
        return new ResponseEntity<>(
                room.orElse(new Room()),
                room.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Room> findByName(@PathVariable String name) {
        var room = roomService.findByName(name);
        return new ResponseEntity<>(
                room.orElse(new Room()),
                room.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Room room = new Room();
        room.setId(id);
        roomService.delete(room);
        return ResponseEntity.ok().build();
    }
}
