package softuni.aggregator.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import softuni.aggregator.service.ImportService;

@Controller
@RequestMapping("/imports")
public class ImportController {

    private final ImportService importService;

    @Autowired
    public ImportController(ImportService importService) {
        this.importService = importService;
    }

    @PostMapping("/xing")
    public ModelAndView importXingFile(ModelAndView model,
                                       @RequestParam("file") MultipartFile file) {
        importService.importCompaniesFromXing(file);
        model.setViewName("redirect:/home");
        return model;
    }

    @PostMapping("/orbis")
    public ModelAndView importOrbisFile(ModelAndView model, @RequestParam("file") MultipartFile file) {
        importService.importCompaniesFromOrbis(file);
        model.setViewName("redirect:/home");
        return model;
    }

    @PostMapping("/employees")
    public ModelAndView importEmployeesFile(ModelAndView model, @RequestParam("file") MultipartFile file) {
        importService.importEmployees(file);
        model.setViewName("redirect:/home");
        return model;
    }
}
