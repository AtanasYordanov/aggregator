package softuni.aggregator.service.excel;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;

public interface ExportExcelService {

    byte[] exportEmployees(HttpServletResponse response) throws FileNotFoundException;

    byte[] exportCompanies(HttpServletResponse response) throws FileNotFoundException;
}
