package ru.kata.spring.boot_security.demo.model;

import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

public interface LoaderExcel {
    List<TestUser> excelTestUsersToEntity(String nameSheet, Workbook excel);

    List<User> excelUsersToEntity(String nameSheet, Workbook excel);
}
