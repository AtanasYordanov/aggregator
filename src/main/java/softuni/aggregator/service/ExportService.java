package softuni.aggregator.service;

import org.springframework.data.domain.Pageable;
import softuni.aggregator.domain.entities.User;
import softuni.aggregator.domain.model.binding.FilterDataModel;
import softuni.aggregator.domain.model.binding.ExportBindingModel;
import softuni.aggregator.domain.model.vo.page.ExportsPageVO;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;

public interface ExportService {

    int exportEmployees(User user, ExportBindingModel exportModel, FilterDataModel filterData) throws FileNotFoundException;

    int exportCompanies(User user, ExportBindingModel exportModel, FilterDataModel filterData);

    byte[] getExport(HttpServletResponse response, Long exportId);

    ExportsPageVO getExportsPage(Pageable pageable, User user);

    long getExportsCountForUser(User user);

    void deleteOldExports();

    int exportEmployeesWithCompanies(User loggedUser, ExportBindingModel exportModel, FilterDataModel filterData);

    ExportsPageVO getAllExportsPage(Pageable pageable);

    long getAllExportsCount();
}
