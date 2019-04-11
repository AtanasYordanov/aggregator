package softuni.aggregator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.aggregator.domain.entities.MajorIndustry;
import softuni.aggregator.domain.entities.MinorIndustry;
import softuni.aggregator.domain.repository.MinorIndustryRepository;
import softuni.aggregator.service.MajorIndustryService;
import softuni.aggregator.service.MinorIndustryService;
import org.springframework.transaction.annotation.Transactional;
import softuni.aggregator.web.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class MinorIndustryServiceImpl implements MinorIndustryService {

    private static final String MAJOR_INDUSTRY_PREFIX = "Maj:";
    private static final String MINOR_INDUSTRY_PREFIX = "Min:";
    private static final String ALL_INDUSTRIES = "all";

    private final MinorIndustryRepository minorIndustryRepository;
    private final MajorIndustryService majorIndustryService;

    @Autowired
    public MinorIndustryServiceImpl(MinorIndustryRepository minorIndustryRepository,
                                    MajorIndustryService majorIndustryService) {
        this.minorIndustryRepository = minorIndustryRepository;
        this.majorIndustryService = majorIndustryService;
    }

    @Override
    public List<String> getAllIndustryNames() {
        return minorIndustryRepository.findAll().stream()
                .map(MinorIndustry::getName)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, MinorIndustry> getAllIndustriesByName() {
        return minorIndustryRepository.findAll().stream()
                .collect(Collectors.toMap(MinorIndustry::getName, i -> i));
    }

    @Override
    public List<MinorIndustry> getIndustries(String industryName) {
        List<MinorIndustry> industries = new ArrayList<>();

        if (industryName == null || industryName.equals(ALL_INDUSTRIES)) {
            return industries;
        } else if (industryName.startsWith(MAJOR_INDUSTRY_PREFIX)) {
            industryName = industryName.substring(MAJOR_INDUSTRY_PREFIX.length());
            MajorIndustry majorIndustry = majorIndustryService.getMajorIndustryByName(industryName);
            industries.addAll(majorIndustry.getMinorIndustries());
        } else if (industryName.startsWith(MINOR_INDUSTRY_PREFIX)) {
            industryName = industryName.substring(MINOR_INDUSTRY_PREFIX.length());
            MinorIndustry minorIndustry = minorIndustryRepository.findByName(industryName)
                    .orElseThrow(() -> new NotFoundException("No such industry."));
            industries.add(minorIndustry);
        }
        return industries;
    }
}
