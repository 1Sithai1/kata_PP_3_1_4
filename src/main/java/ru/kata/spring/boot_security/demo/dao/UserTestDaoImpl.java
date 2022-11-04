package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserTestDaoImpl {

    @PersistenceContext
    private EntityManager entityManager;
}
