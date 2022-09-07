package ru.job4j.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.domain.Person;
import ru.job4j.service.PersonService;
import ru.job4j.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final PersonService personService;
    private final RoomService roomService;
    private final BCryptPasswordEncoder encoder;

    public UserController(PersonService personService, RoomService roomService, BCryptPasswordEncoder encoder) {
        this.personService = personService;
        this.roomService = roomService;
        this.encoder = encoder;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Person> signUp(@RequestBody Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        return new ResponseEntity<>(
                personService.save(person),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/currentUser")
    public String currentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @GetMapping("/all")
    public List<Person> findAll() {
        return personService.findAll();
    }

    @PutMapping("/joinRoom/{name}")
    public ResponseEntity<Void> joinRoom(@PathVariable String name) {
        var person = personService
                .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .get();
        var room = roomService.findByName(name);
        if (room.isPresent()) {
            person.setRoom(room.get());
            personService.save(person);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/roomUsers/id/{id}")
    public List<Person> findAllRoomUsersById(@PathVariable int id) {
        return personService.findAllByRoomId(id);
    }

    @GetMapping("/roomUsers/name/{name}")
    public List<Person> findAllRoomUsersByName(@PathVariable String name) {
        return personService.findAllByRoomName(name);
    }
}
