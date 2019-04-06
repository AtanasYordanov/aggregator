package softuni.aggregator.service;

import softuni.aggregator.domain.entities.MinorIndustry;

import java.util.List;

public interface MinorIndustryService {

    List<String> getAllIndustryNames();

    MinorIndustry getIndustryByName(String name);
}
