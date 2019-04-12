package softuni.aggregator.service;

import softuni.aggregator.domain.entities.SubIndustry;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface SubIndustryService {

    List<String> getAllIndustryNames();

    Map<String, SubIndustry> getAllIndustriesByName();

    List<SubIndustry> getIndustries(String industryName);

    void saveAll(Collection<SubIndustry> subIndustries);
}
