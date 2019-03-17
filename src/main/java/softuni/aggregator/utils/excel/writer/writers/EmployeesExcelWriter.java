package softuni.aggregator.utils.excel.writer.writers;

import org.springframework.stereotype.Component;
import softuni.aggregator.utils.excel.constants.ExcelConstants;
import softuni.aggregator.utils.excel.writer.columns.ExportEmployeesColumn;
import softuni.aggregator.utils.excel.writer.columns.WriteExcelColumn;
import softuni.aggregator.utils.excel.writer.model.WriteEmployeesExcelDto;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EmployeesExcelWriter extends BaseExcelWriter<WriteEmployeesExcelDto> {

    @Override
    public File writeExcel(List<WriteEmployeesExcelDto> employeesDto) {
        Map<Integer, WriteExcelColumn> columns = Arrays.stream(ExportEmployeesColumn.values())
                .collect(Collectors.toMap(Enum::ordinal, e -> e,
                        (u, v) -> {
                            throw new IllegalStateException(String.format("Duplicate key %s", u));
                        }, LinkedHashMap::new));

        return super.writeExcel(employeesDto, columns);
    }

    @Override
    protected String getExportType() {
        return ExcelConstants.EMPLOYEES_EXPORT_NAME;
    }
}
