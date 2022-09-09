package ru.job4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.domain.Person;
import ru.job4j.service.PersonService;
import ru.job4j.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class.getSimpleName());

    private final PersonService personService;
    private final RoomService roomService;
    private final BCryptPasswordEncoder encoder;
    private final ObjectMapper objectMapper;

    public UserController(PersonService personService, RoomService roomService, BCryptPasswordEncoder encoder, ObjectMapper objectMapper) {
        this.personService = personService;
        this.roomService = roomService;
        this.encoder = encoder;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Person> signUp(@RequestBody Person person) {
        var username = person.getUsername();
        var password = person.getPassword();
        if (username == null || password == null) {
            throw new NullPointerException("Username and password mustn't be empty");
        }
        if (username.length() < 3 || password.length() < 5) {
            throw new IllegalArgumentException("Invalid username or password. "
                    + "Username length must be more then 3. "
                    + "Password length must be more then 5.");
        }
        person.setPassword(encoder.encode(person.getPassword()));
        return new ResponseEntity<>(
                personService.save(person),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/currentUser")
    public String currentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        if ("anonymousUser".equals(username)) {
            throw new SecurityException("User isn't sign in.");
        }
        return username;
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
        roomService.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Room is not found. Please, check id."
        ));
        return personService.findAllByRoomId(id);
    }

    @GetMapping("/roomUsers/name/{name}")
    public List<Person> findAllRoomUsersByName(@PathVariable String name) {
        roomService.findByName(name).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Room is not found. Please, check name."
        ));
        return personService.findAllByRoomName(name);
    }

    @ExceptionHandler(value = { SecurityException.class })
    public void exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() { {
            put("message", e.getMessage());
            put("type", e.getClass());
        }}));
        LOGGER.error(e.getLocalizedMessage());
    }
}
