package softuni.aggregator.service;

import softuni.aggregator.domain.entities.MinorIndustry;

import java.util.List;
import java.util.Map;

public interface MinorIndustryService {

    List<String> getAllIndustryNames();

    Map<String, MinorIndustry> getAllIndustriesByName();

    List<MinorIndustry> getIndustries(String industryName);
}
