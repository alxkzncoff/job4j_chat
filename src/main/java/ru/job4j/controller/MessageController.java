package ru.job4j.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.domain.Message;
import ru.job4j.domain.Person;
import ru.job4j.service.MessageService;
import ru.job4j.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final static Logger LOGGER = LoggerFactory.getLogger(MessageController.class.getSimpleName());

    private final MessageService messageService;
    private final PersonService personService;

    public MessageController(MessageService messageService, PersonService personService) {
        this.messageService = messageService;
        this.personService = personService;
    }

    @PostMapping("/")
    public ResponseEntity<HashMap<Object, Object>> send(@Valid @RequestBody Message message) {
        if (message.getTxt() == null) {
            throw new NullPointerException("Message text mustn't be empty.");
        }
        var person = personService
                .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .get();
        if (person.getRoom() == null) {
            throw new NullPointerException("Can't send message out of room. Join room first.");
        }
        message.setPerson(person);
        message.setRoom(person.getRoom());
        var body = new HashMap<>() {{
            put(person.getUsername(), messageService.save(message).getTxt());
        }};
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }

    @GetMapping("/roomId/{id}")
    public ResponseEntity<List<Message>> findAllByRoomId(@PathVariable int id) {
        return ResponseEntity.ok(messageService.findAllByRoomId(id));
    }

    @GetMapping("/roomName/{name}")
    public ResponseEntity<List<Message>> findAllByRoomId(@PathVariable String name) {
        return ResponseEntity.ok(messageService.findAllByRoomName(name));
    }

    @GetMapping("/")
    public ResponseEntity<List<Message>> findAll() {
        return ResponseEntity.ok(messageService.findAll());
    }
}
