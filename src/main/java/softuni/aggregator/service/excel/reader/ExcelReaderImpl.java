package softuni.aggregator.service.excel.reader;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import softuni.aggregator.service.excel.common.ExcelColumn;
import softuni.aggregator.service.excel.reader.columns.ReadExcelColumn;
import softuni.aggregator.service.excel.reader.imports.ImportType;
import softuni.aggregator.service.excel.reader.model.ExcelImportDto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExcelReaderImpl implements ExcelReader {

    @Override
    @SuppressWarnings("unchecked")
    public List<ExcelImportDto> readExcel(String path, ImportType excelImportType) {
        try (FileInputStream inputStream = new FileInputStream(new File(path));
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            Map<String, ReadExcelColumn> columns = Arrays.stream(excelImportType.getColumns())
                    .collect(Collectors.toMap(ExcelColumn::getColumnName, c -> c));

            ReadExcelColumn[] columnsByIndex = getColumnsByIndex(sheet, columns);

            List<ExcelImportDto> data = new ArrayList<>();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                ExcelImportDto dto = excelImportType.createInstance();
                parseRow(row, dto, columnsByIndex);
                data.add(dto);
            }

            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void parseRow(Row row, ExcelImportDto employee, ReadExcelColumn[] columns) {
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            ReadExcelColumn column = columns[cell.getColumnIndex()];
            if (column != null) {
                employee.setProperty(column, getCellValueAsString(cell));
            }
        }
    }

    private ReadExcelColumn[] getColumnsByIndex(Sheet sheet, Map<String, ReadExcelColumn> columns) {
        Row headerRow = sheet.getRow(0);
        ReadExcelColumn[] columnsByIndex = new ReadExcelColumn[headerRow.getPhysicalNumberOfCells()];

        Iterator<Cell> cellIterator = headerRow.cellIterator();
        int cellIndex = 0;

        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            if (cell != null) {
                String columnName = cell.getStringCellValue();
                columnsByIndex[cellIndex] = columns.get(columnName);
            }
            cellIndex++;
        }

        if (Arrays.stream(columnsByIndex).filter(Objects::isNull).count() > columnsByIndex.length / 2) {
            throw new IllegalArgumentException("Wrong export type selected.");
        }

        return columnsByIndex;
    }

    private String getCellValueAsString(Cell cell) {
        return cell.getCellType() == CellType.NUMERIC
                ? String.valueOf(cell.getNumericCellValue())
                : cell.getStringCellValue();
    }
}
