package softuni.aggregator.service.excel.reader.imports;

import lombok.AllArgsConstructor;
import lombok.Getter;
import softuni.aggregator.constants.StringConstants;
import softuni.aggregator.service.excel.reader.columns.EmployeesImportColumn;
import softuni.aggregator.service.excel.reader.columns.OrbisImportColumn;
import softuni.aggregator.service.excel.reader.columns.ReadExcelColumn;
import softuni.aggregator.service.excel.reader.columns.XingImportColumn;
import softuni.aggregator.service.excel.reader.model.EmployeeImportDto;
import softuni.aggregator.service.excel.reader.model.ExcelImportDto;
import softuni.aggregator.service.excel.reader.model.OrbisCompanyImportDto;
import softuni.aggregator.service.excel.reader.model.XingCompanyImportDto;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum ImportType {

    EMPLOYEES("employees", EmployeesImportColumn.values(), EmployeeImportDto::new),
    XING_COMPANIES("xing", XingImportColumn.values(), XingCompanyImportDto::new),
    ORBIS_COMPANIES("orbis", OrbisImportColumn.values(), OrbisCompanyImportDto::new);

    private String endpoint;
    private ReadExcelColumn[] columns;
    private Supplier<ExcelImportDto> instanceCreator;

    public ExcelImportDto createInstance() {
        return instanceCreator.get();
    }

    public String getEndpoint() {
        return this.endpoint;
    }

    @Override
    public String toString() {
        return Arrays.stream(name().split(StringConstants.ENUM_DELIMITER))
                .map(word -> word.charAt(0) + word.substring(1).toLowerCase())
                .collect(Collectors.joining(StringConstants.ENUM_JOIN_DELIMITER));
    }
}
