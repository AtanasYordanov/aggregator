package softuni.aggregator.service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import softuni.aggregator.domain.entities.User;
import softuni.aggregator.domain.model.vo.page.ImportsPageVO;

import java.util.Map;

public interface ImportService {

    ImportsPageVO getImportsPage(Pageable pageable, User user);

    long getImportsCountForUser(User user);

    int importCompaniesFromXing(User user, MultipartFile file);

    int importCompaniesFromOrbis(User user, MultipartFile file);

    int importEmployees(User user, MultipartFile file);

    ImportsPageVO getAllImportsPage(Pageable pageable);

    long getAllImportsCount();

    Map<String, String> getImportTypes();
}
