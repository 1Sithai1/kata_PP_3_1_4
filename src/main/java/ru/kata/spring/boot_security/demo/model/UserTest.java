package ru.kata.spring.boot_security.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class UserTest {
    @Id
    private Long id;

    public UserTest() {
    }

    public UserTest(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserTest{" +
                "id=" + id +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTest userTest = (UserTest) o;
        return Objects.equals(id, userTest.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
