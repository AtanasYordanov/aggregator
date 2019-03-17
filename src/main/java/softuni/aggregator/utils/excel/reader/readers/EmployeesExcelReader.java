package softuni.aggregator.utils.excel.reader.readers;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import softuni.aggregator.utils.excel.reader.BaseExcelReader;
import softuni.aggregator.utils.excel.reader.columns.EmployeeColumn;
import softuni.aggregator.utils.excel.reader.columns.ReadExcelColumn;
import softuni.aggregator.utils.excel.reader.model.EmployeeExcelDto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Qualifier("employees")
public class EmployeesExcelReader extends BaseExcelReader<EmployeeExcelDto> {

    @Override
    public List<EmployeeExcelDto> readExcel(String path) {
        try (FileInputStream inputStream = new FileInputStream(new File(path));
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            Map<String, EmployeeColumn> columns = Arrays.stream(EmployeeColumn.values())
                    .collect(Collectors.toMap(EmployeeColumn::getColumnName, e -> e));

            ReadExcelColumn[] columnsByIndex = getColumnsByIndex(sheet, columns);

            List<EmployeeExcelDto> employees = new ArrayList<>();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                EmployeeExcelDto employee = new EmployeeExcelDto();
                parseRow(row, employee, columnsByIndex);

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
