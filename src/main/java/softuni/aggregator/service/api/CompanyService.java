package softuni.aggregator.service.api;

import softuni.aggregator.service.excel.writer.model.CompaniesExportExcelDto;

import java.util.List;

public interface CompanyService {

    List<CompaniesExportExcelDto> getCompaniesForExport();
}
