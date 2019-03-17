package softuni.aggregator.utils.excel.writer.writers;

import lombok.extern.java.Log;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import softuni.aggregator.utils.excel.constants.ExcelConstants;
import softuni.aggregator.utils.excel.writer.ExcelWriter;
import softuni.aggregator.utils.excel.writer.columns.WriteExcelColumn;
import softuni.aggregator.utils.excel.writer.model.WriteExcelDto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Log
public abstract class BaseExcelWriter<T extends WriteExcelDto> implements ExcelWriter<T> {

    protected File writeExcel(List<T> excelDto, Map<Integer, WriteExcelColumn> columns) {
        String filePath = ExcelConstants.EXPORT_BASE_PATH + generateFileName();

        try (FileOutputStream fileOut = new FileOutputStream(filePath);
             Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet();

            int rowIndex = 0;
            Row headerRow = sheet.createRow(rowIndex++);
            createHeaderRow(columns, workbook, headerRow);

            int columnIndex = 0;
            for (T data : excelDto) {
                Row row = sheet.createRow(rowIndex++);

                columnIndex = 0;
                for (WriteExcelColumn column : columns.values()) {
                    Cell cell = row.createCell(columnIndex++);
                    setCellValue(cell, data.getProperty(column));
                }
            }

            for (int i = 0; i < columnIndex; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(fileOut);
            return new File(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected abstract String getExportType();

    private void createHeaderRow(Map<Integer, WriteExcelColumn> columns, Workbook workbook, Row headerRow) {
        int columnIndex = 0;
        for (WriteExcelColumn column : columns.values()) {
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
            cell.setCellValue(column.getColumnName());
            cell.setCellStyle(style);
        }
    }

    private void setCellValue(Cell cell, Object property) {
        if (property instanceof Integer) {
            cell.setCellValue(Double.valueOf((Integer) property));
        } else if (property instanceof String) {
            cell.setCellValue((String) property);
        } else {
            log.warning(String.format("Unexpected cell type: %s", cell.getCellType().name()));
        }
    }

    private String generateFileName() {
        LocalDateTime now = LocalDateTime.now();
        return String.format("%s__%02d_%02d_%d__%02d_%02d_%02d%s",
                getExportType(),
                now.getDayOfMonth(),
                now.getMonthValue(),
                now.getYear(),
                now.getHour(),
                now.getMinute(),
                now.getSecond(),
                ExcelConstants.FILE_EXTENSION);
    }
}
