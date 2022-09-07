package ru.job4j.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "of")
@Entity
@Table(name = "persons")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "ROLE_ID_FK"))
    private Role role;

    @ManyToOne
    @JoinColumn(name = "room_id", foreignKey = @ForeignKey(name = "ROOM_ID_FK"))
    private Room room;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        return id == person.id && Objects.equals(username, person.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }
}
