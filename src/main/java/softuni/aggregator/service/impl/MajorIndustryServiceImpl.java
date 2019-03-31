package softuni.aggregator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.aggregator.domain.entities.MajorIndustry;
import softuni.aggregator.domain.repository.MajorIndustryRepository;
import softuni.aggregator.service.MajorIndustryService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MajorIndustryServiceImpl implements MajorIndustryService {

    private final MajorIndustryRepository majorIndustryRepository;

    @Autowired
    public MajorIndustryServiceImpl(MajorIndustryRepository majorIndustryRepository) {
        this.majorIndustryRepository = majorIndustryRepository;
    }

    @Override
    public List<String> getAllIndustryNames() {
        return majorIndustryRepository.findAll().stream()
                .map(MajorIndustry::getName)
                .collect(Collectors.toList());
    }

    @Override
    public MajorIndustry getMajorIndustryByName(String name) {
        return majorIndustryRepository.findByName(name).orElseThrow();
    }
}
