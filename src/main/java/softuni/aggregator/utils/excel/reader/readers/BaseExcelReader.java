package softuni.aggregator.utils.excel.reader.readers;

import lombok.extern.java.Log;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import softuni.aggregator.utils.excel.reader.ExcelReader;
import softuni.aggregator.utils.excel.reader.columns.ReadExcelColumn;
import softuni.aggregator.utils.excel.reader.model.ReadExcelDto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Log
public abstract class BaseExcelReader<T extends ReadExcelDto> implements ExcelReader<T> {

    protected List<T> readExcel(String path, Map<String, ReadExcelColumn> columns) {
        try (FileInputStream inputStream = new FileInputStream(new File(path));
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            ReadExcelColumn[] columnsByIndex = getColumnsByIndex(sheet, columns);

            List<T> data = new ArrayList<>();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }

                T dto = createInstance();
                parseRow(row, dto, columnsByIndex);

                data.add(dto);
            }

            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    protected abstract T createInstance();

    private void parseRow(Row row, ReadExcelDto employee, ReadExcelColumn[] columns) {
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            ReadExcelColumn column = columns[cell.getColumnIndex()];

            if (column == null) {
                log.warning(String.format("Unexpected column index: %s", cell.getColumnIndex()));
                continue;
            }

            employee.setProperty(column, getCellValueAsString(cell));
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

        return columnsByIndex;
    }

    private String getCellValueAsString(Cell cell) {
        return cell.getCellType() == CellType.NUMERIC
                ? String.valueOf(cell.getNumericCellValue())
                : cell.getStringCellValue();
    }
}
