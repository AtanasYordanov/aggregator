package softuni.aggregator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.aggregator.domain.entities.MajorIndustry;
import softuni.aggregator.domain.entities.MinorIndustry;
import softuni.aggregator.domain.repository.MinorIndustryRepository;
import softuni.aggregator.service.MinorIndustryService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class MinorIndustryServiceImpl implements MinorIndustryService {

    private final MinorIndustryRepository minorIndustryRepository;

    @Autowired
    public MinorIndustryServiceImpl(MinorIndustryRepository minorIndustryRepository) {
        this.minorIndustryRepository = minorIndustryRepository;
    }

    @Override
    public List<String> getAllIndustryNames() {
        return minorIndustryRepository.findAll().stream()
                .map(MinorIndustry::getName)
                .collect(Collectors.toList());
    }

    @Override
    public MinorIndustry getIndustryByName(String name) {
        return minorIndustryRepository.findByName(name).orElseThrow();
    }

    @Override
    public Map<String, MinorIndustry> getAllIndustriesByName() {
        return minorIndustryRepository.findAll().stream()
                .collect(Collectors.toMap(MinorIndustry::getName, i -> i));
    }
}
