package softuni.aggregator.domain.model.vo.page;

import lombok.Getter;
import lombok.Setter;
import softuni.aggregator.domain.model.vo.CompanyListVO;

import java.util.List;

@Getter
@Setter
public class CompaniesPageVO extends BasePageVO {

    private List<CompanyListVO> companies;
    private List<String> minorIndustries;
    private List<String> majorIndustries;
}
