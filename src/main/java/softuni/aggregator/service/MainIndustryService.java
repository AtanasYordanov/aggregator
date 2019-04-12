package softuni.aggregator.service;

import softuni.aggregator.domain.entities.MainIndustry;

import java.util.List;
import java.util.Map;

public interface MainIndustryService {

    List<String> getAllIndustryNames();

    MainIndustry getMainIndustryByName(String name);

    Map<String, MainIndustry> getAllIndustriesByName();
}