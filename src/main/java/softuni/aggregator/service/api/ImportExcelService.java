package softuni.aggregator.service.api;

import org.springframework.web.multipart.MultipartFile;

public interface ImportExcelService {

    void importCompaniesFromXing(MultipartFile file);

    void importCompaniesFromOrbis(MultipartFile file);

    void importEmployees(MultipartFile file);
}
