package softuni.aggregator.service;

import org.springframework.data.domain.Pageable;
import softuni.aggregator.domain.model.binding.CompaniesFilterDataModel;
import softuni.aggregator.domain.model.vo.CompanyListVO;
import softuni.aggregator.service.excel.writer.model.ExcelExportDto;

import java.util.List;

public interface CompanyService {

    List<ExcelExportDto> getCompaniesForExport(CompaniesFilterDataModel filterData);

    List<CompanyListVO> getCompanies(Pageable pageable);

    List<CompanyListVO> getCompaniesPage(Pageable pageable, CompaniesFilterDataModel filterData);

    long getTotalCompaniesCount();

    long getCompaniesCountForIndustry(String industry);
}
