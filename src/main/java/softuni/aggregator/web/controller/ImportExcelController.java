package softuni.aggregator.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import softuni.aggregator.service.excel.ImportExcelService;

import java.io.IOException;

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
                                       @RequestParam("file") MultipartFile file) throws IOException {

        long start = System.currentTimeMillis();
        importExcelService.importCompaniesFromXing(file);
        long end = System.currentTimeMillis();
        System.out.println("Xing: " + (end - start));

        model.setViewName("redirect:/");
        return model;
    }

    @PostMapping("/orbis")
    public ModelAndView uploadOrbisFile(ModelAndView model,
                                        @RequestParam("file") MultipartFile file) throws IOException {

        long start = System.currentTimeMillis();
        importExcelService.importCompaniesFromOrbis(file);
        long end = System.currentTimeMillis();
        System.out.println("Orbis: " + (end - start));

        model.setViewName("redirect:/");
        return model;
    }

    @PostMapping("/employees")
    public ModelAndView uploadEmployeesFile(ModelAndView model,
                                            @RequestParam("file") MultipartFile file) throws IOException {

        long start = System.currentTimeMillis();
        importExcelService.importEmployees(file);
        long end = System.currentTimeMillis();
        System.out.println("Employees: " + (end - start));

        model.setViewName("redirect:/");
        return model;
    }
}
