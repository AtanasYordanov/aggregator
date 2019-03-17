package softuni.aggregator.web.controller;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import softuni.aggregator.service.excel.ExportExcelService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log
@Controller
@RequestMapping("/export")
public class ExportExcelController {

    private final ExportExcelService exportExcelService;

    @Autowired
    public ExportExcelController(ExportExcelService exportExcelService) {
        this.exportExcelService = exportExcelService;
    }

    @GetMapping(produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    byte[] exportEmployees(HttpServletResponse response) throws IOException {
        return exportExcelService.exportEmployees(response);
    }
}
