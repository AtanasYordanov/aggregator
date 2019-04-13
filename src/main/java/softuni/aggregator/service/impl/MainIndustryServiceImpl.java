package softuni.aggregator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import softuni.aggregator.domain.entities.MainIndustry;
import softuni.aggregator.domain.model.vo.page.FilterPageVO;
import softuni.aggregator.domain.repository.MainIndustryRepository;
import softuni.aggregator.service.MainIndustryService;
import org.springframework.transaction.annotation.Transactional;
import softuni.aggregator.service.SubIndustryService;
import softuni.aggregator.web.exceptions.NotFoundException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class MainIndustryServiceImpl implements MainIndustryService {

    private final MainIndustryRepository mainIndustryRepository;
    private final SubIndustryService subIndustryService;

    @Autowired
    public MainIndustryServiceImpl(MainIndustryRepository mainIndustryRepository,
                                   @Lazy SubIndustryService subIndustryService) {
        this.mainIndustryRepository = mainIndustryRepository;
        this.subIndustryService = subIndustryService;
    }

    @Override
    public Map<String, MainIndustry> getAllIndustriesByName() {
        return mainIndustryRepository.findAll().stream()
                .collect(Collectors.toMap(MainIndustry::getName, i -> i));
    }

    @Override
    public MainIndustry getMainIndustryByName(String name) {
        return mainIndustryRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("No such industry."));
    }

    @Override
    public void fillFilterPageVO(FilterPageVO filterPageVO) {
        List<String> subIndustries = subIndustryService.getAllIndustryNames();
        List<String> mainIndustries = mainIndustryRepository.findAll().stream()
                .map(MainIndustry::getName)
                .collect(Collectors.toList());

        filterPageVO.setSubIndustries(subIndustries);
        filterPageVO.setMainIndustries(mainIndustries);
    }
}
