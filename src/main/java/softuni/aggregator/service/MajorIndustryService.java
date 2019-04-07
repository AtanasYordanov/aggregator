package softuni.aggregator.service;

import softuni.aggregator.domain.entities.MajorIndustry;

import java.util.List;
import java.util.Map;

public interface MajorIndustryService {

    List<String> getAllIndustryNames();

    MajorIndustry getMajorIndustryByName(String name);

    Map<String, MajorIndustry> getAllIndustriesByName();
}
