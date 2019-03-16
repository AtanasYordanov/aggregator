package softuni.aggregator.utils.excelreader;

import lombok.extern.java.Log;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import softuni.aggregator.utils.excelreader.columns.ExcelColumn;
import softuni.aggregator.utils.excelreader.model.BaseExcelDto;
import softuni.aggregator.utils.excelreader.model.EmployeesExcelDto;

import java.util.Iterator;
import java.util.Map;

@Log
public abstract class BaseExcelReader<T extends BaseExcelDto> implements ExcelReader<T> {

    @SuppressWarnings("unchecked")
    protected void parseRow(Row row, BaseExcelDto employee, Map<Integer, ? extends ExcelColumn> columns) {
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            ExcelColumn<EmployeesExcelDto> column = columns.get(cell.getColumnIndex());
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
