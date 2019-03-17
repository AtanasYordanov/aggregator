package softuni.aggregator.utils.excel.reader;

import lombok.extern.java.Log;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import softuni.aggregator.utils.excel.reader.columns.ReadExcelColumn;
import softuni.aggregator.utils.excel.reader.model.ReadExcelDto;
import softuni.aggregator.utils.excel.reader.model.EmployeeExcelDto;

import java.util.Iterator;
import java.util.Map;

@Log
public abstract class BaseExcelReader<T extends ReadExcelDto> implements ExcelReader<T> {

    @SuppressWarnings("unchecked")
    protected void parseRow(Row row, ReadExcelDto employee, Map<Integer, ? extends ReadExcelColumn> columns) {
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            ReadExcelColumn<EmployeeExcelDto> column = columns.get(cell.getColumnIndex());
            if (column == null) {
                log.warning(String.format("Unexpected column index: %s", cell.getColumnIndex()));
                continue;
            }
            employee.setProperty(column, getCellValueAsString(cell));
        }
    }

    private String getCellValueAsString(Cell cell) {
        return cell.getCellType() == CellType.NUMERIC
                ? String.valueOf(cell.getNumericCellValue())
                : cell.getStringCellValue();
    }
}
