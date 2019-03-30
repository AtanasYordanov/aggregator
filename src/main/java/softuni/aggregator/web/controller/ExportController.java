package softuni.aggregator.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import softuni.aggregator.domain.model.binding.CompaniesFilterDataModel;
import softuni.aggregator.service.excel.ExportService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/exports")
public class ExportController {

    private final ExportService exportService;

    @Autowired
    public ExportController(ExportService exportService) {
        this.exportService = exportService;
    }

    @GetMapping
    public ModelAndView getAllExports(ModelAndView model) {
        model.addObject("exports", exportService.getAllExports());
        model.setViewName("exports");
        return model;
    }

    @GetMapping(value = "/employees")
    @ResponseBody
    public void exportEmployees() throws IOException {
        exportService.exportEmployees();
    }

    @GetMapping(value = "/companies")
    @ResponseBody
    public void exportCompanies(CompaniesFilterDataModel filterData) {
        exportService.exportCompanies(filterData);
    }

    @GetMapping(value = "/{exportId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    byte[] downloadExport(HttpServletResponse response, @PathVariable Long exportId) {
        return exportService.getExport(response, exportId);
    }
}
