package softuni.aggregator.service;

import org.springframework.data.domain.Pageable;
import softuni.aggregator.domain.entities.Company;
import softuni.aggregator.domain.model.binding.FilterDataModel;
import softuni.aggregator.domain.model.vo.CompanyDetailsVO;
import softuni.aggregator.domain.model.vo.CompanyListVO;
import softuni.aggregator.service.excel.writer.model.ExcelExportDto;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface CompanyService {

    List<ExcelExportDto> getCompaniesForExport(FilterDataModel filterData);

    List<CompanyListVO> getCompaniesPage(Pageable pageable, FilterDataModel filterData);

    long getFilteredCompaniesCount(FilterDataModel filterData);

    CompanyDetailsVO getById(Long id);

    void saveCompanies(Collection<Company> companies);

    Map<String, Company> getCompaniesByWebsite(List<String> companyWebsites);

    void deleteCompany(Long id);
}
