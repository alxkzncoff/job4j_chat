package ru.job4j.service;

import org.springframework.stereotype.Service;
import ru.job4j.domain.Role;
import ru.job4j.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository store;

    public RoleService(RoleRepository store) {
        this.store = store;
    }

    public Role save(Role role) {
        return store.save(role);
    }

    public Optional<Role> findById(int id) {
        return store.findById(id);
    }

    public List<Role> findAll() {
        return store.findAll();
    }

    public void delete(Role role) {
        store.delete(role);
    }
}
