package softuni.aggregator.utils.excel.writer.writers;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import softuni.aggregator.utils.excel.writer.ExcelWriter;
import softuni.aggregator.utils.excel.writer.columns.ExportEmployeesColumn;
import softuni.aggregator.utils.excel.writer.model.WriteEmployeesExcelDto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EmployeesExcelWriter implements ExcelWriter<WriteEmployeesExcelDto> {

    @Override
    public File writeExcel(List<WriteEmployeesExcelDto> employeesDto) {

        Map<String, ExportEmployeesColumn> columns = Arrays.stream(ExportEmployeesColumn.values())
                .collect(Collectors.toMap(ExportEmployeesColumn::getColumnName, e -> e,
                        (u, v) -> {
                            throw new IllegalStateException(String.format("Duplicate key %s", u));
                        }, LinkedHashMap::new));

        try (FileOutputStream fileOut = new FileOutputStream("D:\\SoftUni\\#Java Web\\Spring Framework\\Project\\aggregator\\src\\main\\resources\\test\\poi-generated-file.xlsx");
             Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet();

            int rowIndex = 0;
            int columnIndex = 0;
            Row headerRow = sheet.createRow(rowIndex++);

            for (String columnName : columns.keySet()) {
                Font font = workbook.createFont();
                font.setColor(IndexedColors.WHITE.getIndex());
                font.setBold(true);

                CellStyle style = workbook.createCellStyle();
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                style.setFont(font);
                style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                style.setAlignment(HorizontalAlignment.CENTER);

                Cell cell = headerRow.createCell(columnIndex++);
                cell.setCellValue(columnName);
                cell.setCellStyle(style);
            }

            for (WriteEmployeesExcelDto employeeData : employeesDto) {
                Row row = sheet.createRow(rowIndex++);

                columnIndex = 0;
                for (ExportEmployeesColumn column : columns.values()) {
                    Cell cell = row.createCell(columnIndex++);
                    setCellValue(cell, employeeData.getProperty(column));
                }
            }

            for (int i = 0; i < columnIndex; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    private void setCellValue(Cell cell, Object property) {
        if (property instanceof Integer) {
            cell.setCellValue(Double.valueOf((Integer)property));
        } else {
            cell.setCellValue((String) property);
        }
    }
}
