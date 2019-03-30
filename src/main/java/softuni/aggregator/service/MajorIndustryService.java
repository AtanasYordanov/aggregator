package softuni.aggregator.service;

import softuni.aggregator.domain.entities.MajorIndustry;

import java.util.List;

public interface MajorIndustryService {

    List<String> getAllIndustryNames();

    MajorIndustry getMajorIndustryByName(String name);
}
