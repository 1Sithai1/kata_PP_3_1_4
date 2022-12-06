package ru.kata.spring.boot_security.demo.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.TestUser;
import ru.kata.spring.boot_security.demo.service.Util.LoaderExcelImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class StartLoadTestExcel extends LoaderExcelImpl implements CommandLineRunner {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

//        String sheetName = excel.getSheetAt(0).getSheetName();
//
//        List<TestUser> users = excelTestUsersToEntity(sheetName, excel);
//
//        users.forEach(testUser -> entityManager.persist(testUser));
    }
}
