package softuni.aggregator.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import softuni.aggregator.domain.entities.User;
import softuni.aggregator.domain.model.binding.CompaniesFilterDataModel;
import softuni.aggregator.domain.model.vo.ExportListVO;
import softuni.aggregator.domain.model.vo.page.ExportsPageVO;
import softuni.aggregator.service.ExportService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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
        List<ExportListVO> exports = exportService.getExportsPage(pageable, loggedUser);
        long exportsCount = exportService.getExportsCount(loggedUser);

        ExportsPageVO exportsPageVO = new ExportsPageVO();
        exportsPageVO.setExports(exports);
        exportsPageVO.setTotalItemsCount(exportsCount);

        return new ResponseEntity<>(exportsPageVO, HttpStatus.OK);
    }

    @GetMapping(value = "/employees", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public int exportEmployees(@AuthenticationPrincipal User loggedUser) throws IOException {
        return exportService.exportEmployees(loggedUser);
    }

    @GetMapping(value = "/companies", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public int exportCompanies(CompaniesFilterDataModel filterData, @AuthenticationPrincipal User loggedUser) {
        return exportService.exportCompanies(filterData, loggedUser);
    }

    @GetMapping(value = "/{exportId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public byte[] downloadExport(HttpServletResponse response, @PathVariable Long exportId) {
        return exportService.getExport(response, exportId);
    }
}
