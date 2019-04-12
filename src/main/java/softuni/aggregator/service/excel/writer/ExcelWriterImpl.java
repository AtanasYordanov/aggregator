package softuni.aggregator.service.excel.writer;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import softuni.aggregator.service.excel.constants.ExcelConstants;
import softuni.aggregator.service.excel.writer.columns.WriteExcelColumn;
import softuni.aggregator.service.excel.writer.exports.ExportType;
import softuni.aggregator.service.excel.writer.model.ExcelExportDto;
import softuni.aggregator.web.exceptions.ServiceException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class ExcelWriterImpl implements ExcelWriter {

    @Override
    @Cacheable("excel")
    public File writeExcel(List<ExcelExportDto> excelDtos, ExportType exportType) {
        if (excelDtos.isEmpty()) {
            throw new IllegalArgumentException("Zero items selected.");
        }

        createDirectoryIfNotExists();

        String fileName = UUID.randomUUID().toString();
        String filePath = ExcelConstants.EXPORT_BASE_PATH + fileName + ExcelConstants.EXPORT_FILE_EXTENSION;

        log.info("______________________________________________________");
        log.info("FILEEEEEE" + filePath);
        log.info("______________________________________________________");

        WriteExcelColumn[] columns = exportType.getColumns();

        try (FileOutputStream fileOut = new FileOutputStream(filePath);
             Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet();
            fillData(excelDtos, columns, workbook, sheet);
            autosizeColumns(columns, sheet);

            workbook.write(fileOut);


            File file = new File(filePath);

            log.info("______________________________________________________");
            log.error("FINAL"+ file.getCanonicalPath());
            log.info("______________________________________________________");
            return new File(filePath);
        } catch (IOException e) {
            log.error("Failed to export %s.", exportType.getExportName());
            throw new ServiceException("Export failed.");
        }
    }

    private void autosizeColumns(WriteExcelColumn[] columns, Sheet sheet) {
        ExecutorService executor = Executors.newFixedThreadPool(columns.length);

        for (int i = 0; i < columns.length; i++) {
            final int columnIndex = i;
            executor.submit(() -> sheet.autoSizeColumn(columnIndex));
        }

        awaitTerminationAfterShutdown(executor);
    }

    private void fillData(List<ExcelExportDto> excelDtos, WriteExcelColumn[] columns, Workbook workbook, Sheet sheet) {
        int rowIndex = 0;
        Row headerRow = sheet.createRow(rowIndex++);
        createHeaderRow(columns, workbook, headerRow);

        for (ExcelExportDto dto : excelDtos) {
            Row row = sheet.createRow(rowIndex++);
            int columnIndex = 0;
            for (WriteExcelColumn column : columns) {
                Cell cell = row.createCell(columnIndex++);
                setCellValue(cell, dto.getProperty(column));
            }
        }
    }

    private void awaitTerminationAfterShutdown(ExecutorService executor) {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(10, TimeUnit.MINUTES)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException ex) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
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
        }
    }

    private void createDirectoryIfNotExists() {
        File dir = new File(ExcelConstants.EXPORT_BASE_PATH);
            log.error("_____________________________________________________");
            log.error("DIRRRRRRRRRRRRR" +  dir.getPath());
            log.error("DIRRRRRRRRRRRRR" +  dir.getAbsolutePath());
            log.error("_____________________________________________________");

        if (!dir.exists()) {
            if (!dir.mkdir()) {
                log.error("Failed to create directory: {}!", dir.getPath());
                throw new ServiceException("Failed to write excel file!");
            }
        }
    }
}
