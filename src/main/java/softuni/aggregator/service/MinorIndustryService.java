package softuni.aggregator.service;

import softuni.aggregator.domain.entities.MajorIndustry;
import softuni.aggregator.domain.entities.MinorIndustry;

import java.util.List;

public interface MinorIndustryService {

    List<String> getAllIndustryNames();

    List<MinorIndustry> getAllIndustriesForMajor(MajorIndustry majorIndustry);

    MinorIndustry getIndustryByName(String name);
}
