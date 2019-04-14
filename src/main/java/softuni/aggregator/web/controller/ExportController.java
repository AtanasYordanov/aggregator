package softuni.aggregator.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import softuni.aggregator.domain.entities.User;
import softuni.aggregator.domain.model.binding.ExportBindingModel;
import softuni.aggregator.domain.model.binding.FilterDataModel;
import softuni.aggregator.domain.model.vo.page.ExportsPageVO;
import softuni.aggregator.service.ExportService;
import softuni.aggregator.service.NewsService;
import softuni.aggregator.web.annotations.Log;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/exports")
public class ExportController {

    private final ExportService exportService;

    @Autowired
    public ExportController(ExportService exportService) {
        this.exportService = exportService;
    }

    @GetMapping
    public ModelAndView exports(ModelAndView model) {
        model.setViewName("exports");
        return model;
    }

    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExportsPageVO> getExportsPage(Pageable pageable, @AuthenticationPrincipal User loggedUser) {
        ExportsPageVO exportsPageVO = exportService.getExportsPage(pageable, loggedUser);
        return new ResponseEntity<>(exportsPageVO, HttpStatus.OK);
    }

    @Log
    @PostMapping(value = "/employees", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> exportEmployees(FilterDataModel filterData
            , @RequestBody ExportBindingModel exportModel, @AuthenticationPrincipal User loggedUser) {

        if (exportModel.getIncludeCompanies()) {
            exportService.exportEmployeesWithCompanies(loggedUser, exportModel, filterData);
        } else {
            exportService.exportEmployees(loggedUser, exportModel, filterData);
        }
        return ResponseEntity.ok().build();
    }

    @Log
    @PostMapping(value = "/companies", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> exportCompanies(FilterDataModel filterData
            , @RequestBody ExportBindingModel exportModel, @AuthenticationPrincipal User loggedUser) {
        exportService.exportCompanies(loggedUser, exportModel, filterData);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{exportId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public byte[] downloadExport(HttpServletResponse response, @PathVariable Long exportId) {
        return exportService.getExport(response, exportId);
    }
}
