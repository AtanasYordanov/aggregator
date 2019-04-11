package softuni.aggregator.service;

import org.springframework.data.domain.Pageable;
import softuni.aggregator.domain.entities.User;
import softuni.aggregator.domain.model.binding.CompaniesFilterDataModel;
import softuni.aggregator.domain.model.binding.ExportBindingModel;
import softuni.aggregator.domain.model.vo.ExportListVO;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.util.List;

public interface ExportService {

    int exportEmployees(User user, ExportBindingModel exportModel) throws FileNotFoundException;

    int exportCompanies(CompaniesFilterDataModel filterData, User user, ExportBindingModel exportModel);

    byte[] getExport(HttpServletResponse response, Long exportId);

    List<ExportListVO> getExportsPage(Pageable pageable, User user);

    long getExportsCount(User user);

    void deleteOldExports();
}
