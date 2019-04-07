package softuni.aggregator.service;

import softuni.aggregator.domain.entities.MinorIndustry;

import java.util.List;
import java.util.Map;

public interface MinorIndustryService {

    List<String> getAllIndustryNames();

    MinorIndustry getIndustryByName(String name);

    Map<String, MinorIndustry> getAllIndustriesByName();
}
