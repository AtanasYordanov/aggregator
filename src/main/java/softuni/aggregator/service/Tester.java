package softuni.aggregator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.aggregator.service.excel.ImportExcelService;

import javax.annotation.PostConstruct;

@Service
public class Tester {

    private final ImportExcelService importExcelService;

    @Autowired
    public Tester(ImportExcelService importExcelService) {
        this.importExcelService = importExcelService;
    }

    @PostConstruct
    public void init() {
        long start = System.currentTimeMillis();
        importExcelService.importCompaniesFromXing();
        long end = System.currentTimeMillis();
        System.out.println("Xing: " + (end - start));
        start = System.currentTimeMillis();
        importExcelService.importCompaniesFromOrbis();
        end = System.currentTimeMillis();
        System.out.println("Orbis: " + (end - start));
        start = System.currentTimeMillis();
        importExcelService.importEmployees();
        end = System.currentTimeMillis();
        System.out.println("Employees: " + (end - start));
    }
}
