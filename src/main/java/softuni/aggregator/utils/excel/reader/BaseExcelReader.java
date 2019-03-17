package softuni.aggregator.utils.excel.reader;

import lombok.extern.java.Log;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import softuni.aggregator.utils.excel.reader.columns.ReadExcelColumn;
import softuni.aggregator.utils.excel.reader.model.ReadExcelDto;
import softuni.aggregator.utils.excel.reader.model.EmployeeExcelDto;

import java.util.Iterator;
import java.util.Map;

@Log
public abstract class BaseExcelReader<T extends ReadExcelDto> implements ExcelReader<T> {

    @SuppressWarnings("unchecked")
    protected void parseRow(Row row, ReadExcelDto employee, ReadExcelColumn[] columns) {
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            ReadExcelColumn<EmployeeExcelDto> column = columns[cell.getColumnIndex()];
            if (column == null) {
                log.warning(String.format("Unexpected column index: %s", cell.getColumnIndex()));
                continue;
            }
            employee.setProperty(column, getCellValueAsString(cell));
        }
    }

    protected ReadExcelColumn[] getColumnsByIndex(Sheet sheet, Map<String, ? extends ReadExcelColumn> columns) {
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
