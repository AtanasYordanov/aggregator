package softuni.aggregator.utils.excelreader.readers;

import lombok.extern.java.Log;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import softuni.aggregator.utils.excelreader.BaseExcelReader;
import softuni.aggregator.utils.excelreader.columns.EmployeeColumn;
import softuni.aggregator.utils.excelreader.model.EmployeesExcelDto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Log
public class EmployeesExcelReader extends BaseExcelReader<EmployeesExcelDto> {

    @Override
    public List<EmployeesExcelDto> readExcel(String path) {
        try (FileInputStream inputStream = new FileInputStream(new File(path));
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            Map<Integer, EmployeeColumn> columns = Arrays.stream(EmployeeColumn.values())
                    .collect(Collectors.toMap(Enum::ordinal, e -> e));

            List<EmployeesExcelDto> employees = new ArrayList<>();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                EmployeesExcelDto employee = new EmployeesExcelDto();

                parseRow(row, employee, columns);
                if (employee.getEmail() != null && !employee.getEmail().isBlank()) {
                    employees.add(employee);
                }
            }

            return employees;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
