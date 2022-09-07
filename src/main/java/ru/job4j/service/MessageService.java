package ru.job4j.service;

import org.springframework.stereotype.Service;
import ru.job4j.domain.Message;
import ru.job4j.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository store;

    public MessageService(MessageRepository store) {
        this.store = store;
    }

    public Message save(Message message) {
        return store.save(message);
    }

    public Optional<Message> findById(int id) {
        return store.findById(id);
    }

    public List<Message> findAllByRoomId(int id) {
        return store.findAllByRoomId(id);
    }

    public List<Message> findAllByRoomName(String name) {
        return store.findAllByRoomName(name);
    }

    public List<Message> findAll() {
        return store.findAll();
    }

    public void delete(Message message) {
        store.delete(message);
    }
}
