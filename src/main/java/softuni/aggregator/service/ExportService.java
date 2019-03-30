package softuni.aggregator.service;

import org.springframework.data.domain.Pageable;
import softuni.aggregator.domain.model.binding.CompaniesFilterDataModel;
import softuni.aggregator.domain.model.vo.ExportListVO;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.util.List;

public interface ExportService {

    int exportEmployees() throws FileNotFoundException;

    int exportCompanies(CompaniesFilterDataModel filterData);

    byte[] getExport(HttpServletResponse response, Long exportId);

    List<ExportListVO> getExportsPage(Pageable pageable);

    long getExportsCount();
}
