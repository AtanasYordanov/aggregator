package softuni.aggregator.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.aggregator.service.excel.ExportExcelService;

@Controller
@RequestMapping("/export")
public class ExportExcelController {

    private final ExportExcelService exportExcelService;

    @Autowired
    public ExportExcelController(ExportExcelService exportExcelService) {
        this.exportExcelService = exportExcelService;
    }

    @GetMapping
    public ModelAndView exportEmployees(ModelAndView model) {
        exportExcelService.exportEmployees();
        model.setViewName("redirect:/");
        return model;
    }
}
