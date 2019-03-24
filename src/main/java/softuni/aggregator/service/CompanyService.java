package softuni.aggregator.service;

import org.springframework.data.domain.Pageable;
import softuni.aggregator.domain.model.vo.CompanyListVO;
import softuni.aggregator.service.excel.writer.model.ExcelExportDto;

import java.util.List;

public interface CompanyService {

    List<ExcelExportDto> getCompaniesForExport();

    List<CompanyListVO> getCompanies(Pageable pageable);

    long getTotalCompaniesCount();
}
