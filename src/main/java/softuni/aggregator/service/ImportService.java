package softuni.aggregator.service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import softuni.aggregator.domain.entities.User;
import softuni.aggregator.domain.model.vo.ImportListVO;

import java.util.List;

public interface ImportService {

    List<ImportListVO> getImportsPage(Pageable pageable, User user);

    long getImportsCount(User user);

    void importCompaniesFromXing(User user, MultipartFile file);

    void importCompaniesFromOrbis(User user, MultipartFile file);

    void importEmployees(User user, MultipartFile file);
}
