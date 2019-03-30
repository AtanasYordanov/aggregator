package softuni.aggregator.domain.model.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompaniesPageVO {

    private List<CompanyListVO> companies;
    private List<String> minorIndustries;
    private List<String> majorIndustries;
    private long totalCompaniesCount;
}
