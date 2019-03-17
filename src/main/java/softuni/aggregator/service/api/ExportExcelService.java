package softuni.aggregator.service.api;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;

public interface ExportExcelService {

    byte[] exportEmployees(HttpServletResponse response) throws FileNotFoundException;
}
