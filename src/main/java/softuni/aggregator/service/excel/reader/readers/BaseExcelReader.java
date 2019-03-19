package softuni.aggregator.service.excel.reader.readers;

import lombok.extern.java.Log;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import softuni.aggregator.service.excel.common.ExcelColumn;
import softuni.aggregator.service.excel.reader.ExcelReader;
import softuni.aggregator.service.excel.reader.columns.ReadExcelColumn;
import softuni.aggregator.service.excel.reader.model.ImportExcelDto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Log
public abstract class BaseExcelReader<T extends ImportExcelDto> implements ExcelReader<T> {

    @Override
    public List<T> readExcel(String path) {
        try (FileInputStream inputStream = new FileInputStream(new File(path));
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            Map<String, ReadExcelColumn> columns = Arrays.stream(getColumns())
                    .collect(Collectors.toMap(ExcelColumn::getColumnName, c -> c));

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

    protected abstract ReadExcelColumn[] getColumns();

    protected abstract T createInstance();

    private void parseRow(Row row, ImportExcelDto employee, ReadExcelColumn[] columns) {
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            ReadExcelColumn column = columns[cell.getColumnIndex()];

            if (column == null) {
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
