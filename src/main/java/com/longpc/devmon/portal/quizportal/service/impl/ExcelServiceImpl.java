package com.longpc.devmon.portal.quizportal.service.impl;

import com.longpc.devmon.portal.quizportal.service.ExcelService;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Long PC
 * 05/03/2024| 12:53 | 2024
 **/
@Component
public class ExcelServiceImpl implements ExcelService {
    @SneakyThrows
    public void readExcel(File input) {
        FileInputStream file = new FileInputStream(input);
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);

        Map<Integer, List<String>> data = new HashMap<>();
        int i = 0;
        for (Row row : sheet) {
            data.put(i, new ArrayList<>());
            for (Cell cell : row) {
                switch (cell.getCellType()) {
                    case STRING: {

                        break;
                    }
                    case NUMERIC: {
                        break;
                    }
                    case BOOLEAN: {
                        break;
                    }
                    case FORMULA: {
                        break;
                    }
                    default:
                        data.get(new Integer(i)).add(" ");
                }
            }
            i++;
        }
    }
}
