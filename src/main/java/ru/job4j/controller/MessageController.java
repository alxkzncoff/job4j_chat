package ru.job4j.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.domain.Message;
import ru.job4j.service.MessageService;
import ru.job4j.service.PersonService;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;
    private final PersonService personService;

    public MessageController(MessageService messageService, PersonService personService) {
        this.messageService = messageService;
        this.personService = personService;
    }

    @PostMapping("/")
    public ResponseEntity<Message> send(@RequestBody Message message) {
        var person = personService
                .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .get();
        message.setPerson(person);
        message.setRoom(person.getRoom());
        return new ResponseEntity<>(
                messageService.save(message),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/roomId/{id}")
    public List<Message> findAllByRoomId(@PathVariable int id) {
        return messageService.findAllByRoomId(id);
    }

    @GetMapping("/roomName/{name}")
    public List<Message> findAllByRoomId(@PathVariable String name) {
        return messageService.findAllByRoomName(name);
    }

    @GetMapping("/")
    public List<Message> findAll() {
        return messageService.findAll();
    }
}
