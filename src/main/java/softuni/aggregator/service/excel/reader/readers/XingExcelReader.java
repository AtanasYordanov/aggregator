package softuni.aggregator.service.excel.reader.readers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import softuni.aggregator.service.excel.reader.columns.ReadExcelColumn;
import softuni.aggregator.service.excel.reader.columns.XingImportColumn;
import softuni.aggregator.service.excel.reader.model.XingCompanyDto;

@Component
@Qualifier("xing")
public class XingExcelReader extends BaseExcelReader<XingCompanyDto> {

    @Override
    protected ReadExcelColumn[] getColumns() {
        return XingImportColumn.values();
    }

    @Override
    protected XingCompanyDto createInstance() {
        return new XingCompanyDto();
    }
}
