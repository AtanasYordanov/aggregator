package softuni.aggregator.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import softuni.aggregator.service.api.ImportExcelService;
import softuni.aggregator.utils.performance.PerformanceUtils;

@Controller
@RequestMapping("/import")
public class ImportExcelController {

    private final ImportExcelService importExcelService;

    @Autowired
    public ImportExcelController(ImportExcelService importExcelService) {
        this.importExcelService = importExcelService;
    }

    @PostMapping("/xing")
    public ModelAndView uploadXingFile(ModelAndView model,
                                       @RequestParam("file") MultipartFile file) {

        PerformanceUtils.logExecutionTime(() -> {
            importExcelService.importCompaniesFromXing(file);
        }, "XING upload");

        model.setViewName("redirect:/");
        return model;
    }

    @PostMapping("/orbis")
    public ModelAndView uploadOrbisFile(ModelAndView model,
                                        @RequestParam("file") MultipartFile file) {

        PerformanceUtils.logExecutionTime(() -> {
            importExcelService.importCompaniesFromOrbis(file);
        }, "Orbis upload");

        model.setViewName("redirect:/");
        return model;
    }

    @PostMapping("/employees")
    public ModelAndView uploadEmployeesFile(ModelAndView model,
                                            @RequestParam("file") MultipartFile file) {

        PerformanceUtils.logExecutionTime(() -> {
            importExcelService.importEmployees(file);
        }, "Employees upload");

        model.setViewName("redirect:/");
        return model;
    }
}
