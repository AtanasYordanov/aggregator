package softuni.aggregator.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import softuni.aggregator.service.excel.ImportExcelService;

@Controller
@RequestMapping("/import")
public class ImportExcelController {

    private final ImportExcelService importExcelService;

    @Autowired
    public ImportExcelController(ImportExcelService importExcelService) {
        this.importExcelService = importExcelService;
    }

    @PostMapping("/xing")
    public ModelAndView importXingFile(ModelAndView model,
                                       @RequestParam("file") MultipartFile file) {
        importExcelService.importCompaniesFromXing(file);
        model.setViewName("redirect:/");
        return model;
    }

    @PostMapping("/orbis")
    public ModelAndView importOrbisFile(ModelAndView model, @RequestParam("file") MultipartFile file) {
        importExcelService.importCompaniesFromOrbis(file);
        model.setViewName("redirect:/");
        return model;
    }

    @PostMapping("/employees")
    public ModelAndView importEmployeesFile(ModelAndView model, @RequestParam("file") MultipartFile file) {
        importExcelService.importEmployees(file);
        model.setViewName("redirect:/");
        return model;
    }
}
