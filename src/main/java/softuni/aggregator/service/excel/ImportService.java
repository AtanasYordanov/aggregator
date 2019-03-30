package softuni.aggregator.service.excel;

import org.springframework.web.multipart.MultipartFile;

public interface ImportService {

    void importCompaniesFromXing(MultipartFile file);

    void importCompaniesFromOrbis(MultipartFile file);

    void importEmployees(MultipartFile file);
}
