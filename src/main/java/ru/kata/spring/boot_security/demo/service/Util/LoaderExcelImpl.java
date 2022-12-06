package ru.kata.spring.boot_security.demo.service.Util;

import lombok.Getter;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.kata.spring.boot_security.demo.model.LoaderExcel;
import ru.kata.spring.boot_security.demo.model.TestUser;
import ru.kata.spring.boot_security.demo.model.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class LoaderExcelImpl implements LoaderExcel {

    private final String url = "C:/Users/trupa/OneDrive/Рабочий стол/test.xlsx";

    protected Workbook excel;

    {
        try {
            excel = new XSSFWorkbook(new FileInputStream(url));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<TestUser> excelTestUsersToEntity(String nameSheet, Workbook excel) {
        int lastRowNum = excel.getSheet(nameSheet).getLastRowNum(); //количество сущностей в таблице = количеству строк excel
        while (excel.getSheet(nameSheet).getRow(lastRowNum).getCell(0).getCellType() == CellType.BLANK) {
            lastRowNum--;
        }
        TestUser testUser;
        List<TestUser> users = new ArrayList<>();
        for (int i = 0; lastRowNum > 0; i++) {
            LocalDate date1 = excel.getSheet(nameSheet).getRow(i + 1).getCell(4).getLocalDateTimeCellValue().toLocalDate();
            testUser = TestUser.builder()
//                    .id((int) excel.getSheet(nameSheet).getRow(i + 1).getCell(0).getNumericCellValue())
                    .name(excel.getSheet(nameSheet).getRow(i + 1).getCell(1).getStringCellValue())
                    .sureName(excel.getSheet(nameSheet).getRow(i + 1).getCell(2).getStringCellValue())
                    .email(excel.getSheet(nameSheet).getRow(i + 1).getCell(3).getStringCellValue())
                    .date(date1)
                    .build();
            lastRowNum--;
            users.add(testUser);
        }
        return users;
    }

    public List<User> excelUsersToEntity(String nameSheet, Workbook excel) {
        int lastRowNum1 = excel.getSheet(nameSheet).getLastRowNum(); //количество сущностей в таблице = количеству строк excel
        User user;
        List<User> users = new ArrayList<>();
        for (int i = 0; lastRowNum1 > 0; i++) {
            user = User.builder()
//                    .id((long) excel.getSheet(nameSheet).getRow(i + 1).getCell(0).getNumericCellValue())
                    .email(excel.getSheet(nameSheet).getRow(i + 1).getCell(1).getStringCellValue())
                    .lastName(excel.getSheet(nameSheet).getRow(i + 1).getCell(2).getStringCellValue())
                    .name(excel.getSheet(nameSheet).getRow(i + 1).getCell(3).getStringCellValue())
                    .age((byte) excel.getSheet(nameSheet).getRow(i + 1).getCell(4).getNumericCellValue())
                    .password(String.valueOf(excel.getSheet(nameSheet).getRow(i + 1).getCell(5).getNumericCellValue()))
                    .roles(null)
                    .build();
            lastRowNum1--;
            users.add(user);
        }
        return users;
    }

}
