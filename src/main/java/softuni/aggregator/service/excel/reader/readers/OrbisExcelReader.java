package softuni.aggregator.service.excel.reader.readers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import softuni.aggregator.service.excel.reader.columns.OrbisImportColumn;
import softuni.aggregator.service.excel.reader.columns.ReadExcelColumn;
import softuni.aggregator.service.excel.reader.model.OrbisCompanyDto;

@Component
@Qualifier("orbis")
public class OrbisExcelReader extends BaseExcelReader<OrbisCompanyDto> {

    @Override
    protected ReadExcelColumn[] getColumns() {
        return OrbisImportColumn.values();
    }

    @Override
    protected OrbisCompanyDto createInstance() {
        return new OrbisCompanyDto();
    }
}
