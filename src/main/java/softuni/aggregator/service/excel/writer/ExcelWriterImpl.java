package softuni.aggregator.service.excel.writer;

import lombok.extern.java.Log;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import softuni.aggregator.service.excel.writer.columns.WriteExcelColumn;
import softuni.aggregator.service.excel.writer.exports.Export;
import softuni.aggregator.service.excel.writer.model.ExcelExportDto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Log
@Service
public class ExcelWriterImpl implements ExcelWriter {

    private static final String NO_INFORMATION = "n/a";
    private static final String EXPORT_BASE_PATH = "src\\main\\resources\\exports\\";
    private static final String FILE_EXTENSION = ".xlsx";

    @Override
    public File writeExcel(List<ExcelExportDto> excelDtos, Export export) {
        String filePath = EXPORT_BASE_PATH + generateFileName(export);

        WriteExcelColumn[] columns = export.getColumns();

        try (FileOutputStream fileOut = new FileOutputStream(filePath);
             Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet();

            int rowIndex = 0;
            Row headerRow = sheet.createRow(rowIndex++);
            createHeaderRow(columns, workbook, headerRow);

            for (ExcelExportDto data : excelDtos) {
                Row row = sheet.createRow(rowIndex++);
                int columnIndex = 0;
                for (WriteExcelColumn column : columns) {
                    Cell cell = row.createCell(columnIndex++);
                    setCellValue(cell, data.getProperty(column));
                }
            }

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(fileOut);
            return new File(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void createHeaderRow(WriteExcelColumn[] columns, Workbook workbook, Row headerRow) {
        int columnIndex = 0;
        for (WriteExcelColumn column : columns) {
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
            style.setWrapText(true);

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
        } else if (cell.getCellType() == CellType.BLANK) {
            cell.setCellValue(NO_INFORMATION);
        }
    }

    private String generateFileName(Export export) {
        LocalDateTime now = LocalDateTime.now();
        return String.format("%s_%02d-%02d-%d_%02d-%02d-%02d%s",
                export.getExportName(),
                now.getDayOfMonth(),
                now.getMonthValue(),
                now.getYear(),
                now.getHour(),
                now.getMinute(),
                now.getSecond(),
                FILE_EXTENSION);
    }
}
