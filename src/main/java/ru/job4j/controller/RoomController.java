package ru.job4j.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.domain.Room;
import ru.job4j.service.PersonService;
import ru.job4j.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final static Logger LOGGER = LoggerFactory.getLogger(RoomController.class.getSimpleName());

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
    public ResponseEntity<Room> create(@Valid @RequestBody Room room) {
        roomNameValidation(room);
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
    public ResponseEntity<Void> update(@Valid @RequestBody Room room) {
        roomNameValidation(room);
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
        var room = roomService.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Room is not found. Please, check id."
        ));
        return new ResponseEntity<>(
                room,
                HttpStatus.OK
        );
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Room> findByName(@PathVariable String name) {
        var room = roomService.findByName(name).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Room is not found. Please, check name."
        ));
        return new ResponseEntity<>(
                room,
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Room room = new Room();
        room.setId(id);
        roomService.delete(room);
        return ResponseEntity.ok().build();
    }

    private void roomNameValidation(Room room) {
        var roomName = room.getName();
        if (roomName == null) {
            throw new NullPointerException("Room name mustn't be empty.");
        }
        if (roomName.length() < 4) {
            throw new IllegalArgumentException("Room name length must be more than 4");
        }
    }
}
