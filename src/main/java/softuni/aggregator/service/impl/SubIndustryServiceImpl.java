package softuni.aggregator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.aggregator.domain.entities.MainIndustry;
import softuni.aggregator.domain.entities.SubIndustry;
import softuni.aggregator.domain.repository.SubIndustryRepository;
import softuni.aggregator.service.MainIndustryService;
import softuni.aggregator.service.SubIndustryService;
import org.springframework.transaction.annotation.Transactional;
import softuni.aggregator.web.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class SubIndustryServiceImpl implements SubIndustryService {

    private static final String MAIN_INDUSTRY_PREFIX = "Main:";
    private static final String SUB_INDUSTRY_PREFIX = "Sub:";
    private static final String ALL_INDUSTRIES = "all";

    private final SubIndustryRepository subIndustryRepository;
    private final MainIndustryService mainIndustryService;

    @Autowired
    public SubIndustryServiceImpl(SubIndustryRepository subIndustryRepository,
                                  MainIndustryService mainIndustryService) {
        this.subIndustryRepository = subIndustryRepository;
        this.mainIndustryService = mainIndustryService;
    }

    @Override
    public List<String> getAllIndustryNames() {
        return subIndustryRepository.findAll().stream()
                .map(SubIndustry::getName)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, SubIndustry> getAllIndustriesByName() {
        return subIndustryRepository.findAll().stream()
                .collect(Collectors.toMap(SubIndustry::getName, i -> i));
    }

    @Override
    public List<SubIndustry> getIndustries(String industryName) {
        List<SubIndustry> industries = new ArrayList<>();

        if (industryName == null || industryName.equals(ALL_INDUSTRIES)) {
            industries = null;
        } else if (industryName.startsWith(MAIN_INDUSTRY_PREFIX)) {
            industryName = industryName.substring(MAIN_INDUSTRY_PREFIX.length());
            MainIndustry mainIndustry = mainIndustryService.getMainIndustryByName(industryName);
            industries.addAll(mainIndustry.getSubIndustries());
        } else if (industryName.startsWith(SUB_INDUSTRY_PREFIX)) {
            industryName = industryName.substring(SUB_INDUSTRY_PREFIX.length());
            SubIndustry subIndustry = subIndustryRepository.findByName(industryName)
                    .orElseThrow(() -> new NotFoundException("No such industry."));
            industries.add(subIndustry);
        }
        return industries;
    }

    @Override
    public void saveAll(Collection<SubIndustry> subIndustries) {
        subIndustryRepository.saveAll(subIndustries);
    }
}
