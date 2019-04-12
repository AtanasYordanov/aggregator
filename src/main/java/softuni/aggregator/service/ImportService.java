package softuni.aggregator.service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import softuni.aggregator.domain.entities.User;
import softuni.aggregator.domain.model.vo.ImportListVO;

import java.util.List;

public interface ImportService {

    List<ImportListVO> getImportsPage(Pageable pageable, User user);

    long getImportsCountForUser(User user);

    int importCompaniesFromXing(User user, MultipartFile file);

    int importCompaniesFromOrbis(User user, MultipartFile file);

    int importEmployees(User user, MultipartFile file);

    List<ImportListVO> getAllImportsPage(Pageable pageable);

    long getAllImportsCount();
}
