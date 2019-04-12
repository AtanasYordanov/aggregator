package softuni.aggregator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.aggregator.domain.entities.MainIndustry;
import softuni.aggregator.domain.repository.MainIndustryRepository;
import softuni.aggregator.service.MainIndustryService;
import org.springframework.transaction.annotation.Transactional;
import softuni.aggregator.web.exceptions.NotFoundException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class MainIndustryServiceImpl implements MainIndustryService {

    private final MainIndustryRepository mainIndustryRepository;

    @Autowired
    public MainIndustryServiceImpl(MainIndustryRepository mainIndustryRepository) {
        this.mainIndustryRepository = mainIndustryRepository;
    }

    @Override
    public List<String> getAllIndustryNames() {
        return mainIndustryRepository.findAll().stream()
                .map(MainIndustry::getName)
                .collect(Collectors.toList());
    }

    @Override
    public MainIndustry getMainIndustryByName(String name) {
        return mainIndustryRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("No such industry."));
    }

    @Override
    public Map<String, MainIndustry> getAllIndustriesByName() {
        return mainIndustryRepository.findAll().stream()
                .collect(Collectors.toMap(MainIndustry::getName, i -> i));
    }
}