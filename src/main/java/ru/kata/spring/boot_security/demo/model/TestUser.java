package ru.kata.spring.boot_security.demo.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "test_users")
public class TestUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "sure_name")
    private String sureName;
    @Column(name = "email")
    private String email;
    @Column(name = "date")
    @Transient
    private LocalDate date;
    @OneToOne
    @JoinColumn(name = "id")
    private MetadataUser metadataUser;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TestUser testUser = (TestUser) o;
        return id != null && Objects.equals(id, testUser.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
