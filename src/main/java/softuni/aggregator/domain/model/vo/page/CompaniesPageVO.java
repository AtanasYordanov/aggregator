package softuni.aggregator.domain.model.vo.page;

import lombok.Getter;
import lombok.Setter;
import softuni.aggregator.domain.model.vo.CompanyListVO;

import java.util.List;

@Getter
@Setter
public class CompaniesPageVO extends FilterPageVO {

    private List<CompanyListVO> companies;
}
