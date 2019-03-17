package softuni.aggregator.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import softuni.aggregator.service.excel.ImportExcelService;

import javax.servlet.ServletContext;
import java.io.IOException;

@Controller
public class HomeController {

    private final ServletContext servletContext;
    private final ImportExcelService importExcelService;

    @Autowired
    public HomeController(ServletContext servletContext, ImportExcelService importExcelService) {
        this.servletContext = servletContext;
        this.importExcelService = importExcelService;
    }

    @GetMapping("/")
    public ModelAndView index(ModelAndView model) {
        model.setViewName("index");
        return model;
    }

    @PostMapping("/upload/xing")
    public ModelAndView uploadXingFile(ModelAndView model,
                                       @RequestParam("file") MultipartFile file) throws IOException {

        long start = System.currentTimeMillis();
        importExcelService.importCompaniesFromXing(file);
        long end = System.currentTimeMillis();
        System.out.println("Xing: " + (end - start));

        model.setViewName("redirect:/");
        return model;
    }

    @PostMapping("/upload/orbis")
    public ModelAndView uploadOrbisFile(ModelAndView model,
                                       @RequestParam("file") MultipartFile file) throws IOException {

        long start = System.currentTimeMillis();
        importExcelService.importCompaniesFromOrbis(file);
        long end = System.currentTimeMillis();
        System.out.println("Orbis: " + (end - start));

        model.setViewName("redirect:/");
        return model;
    }

    @PostMapping("/upload/employees")
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
