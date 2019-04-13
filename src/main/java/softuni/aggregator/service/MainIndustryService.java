package softuni.aggregator.service;

import softuni.aggregator.domain.entities.MainIndustry;
import softuni.aggregator.domain.model.vo.page.FilterPageVO;

import java.util.Map;

public interface MainIndustryService {

    MainIndustry getMainIndustryByName(String name);

    Map<String, MainIndustry> getAllIndustriesByName();

    void fillFilterPageVO(FilterPageVO filterPageVO);
}
