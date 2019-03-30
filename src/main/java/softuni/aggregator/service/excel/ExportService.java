package softuni.aggregator.service.excel;

import softuni.aggregator.domain.model.binding.CompaniesFilterDataModel;
import softuni.aggregator.domain.model.vo.ExportListVO;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.util.List;

public interface ExportService {

    void exportEmployees() throws FileNotFoundException;

    void exportCompanies(CompaniesFilterDataModel filterData);

    byte[] getExport(HttpServletResponse response, Long exportId);

    List<ExportListVO> getAllExports();
}
