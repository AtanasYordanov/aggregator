package softuni.aggregator.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import softuni.aggregator.domain.entities.User;
import softuni.aggregator.domain.model.vo.page.ImportsPageVO;
import softuni.aggregator.service.ImportService;
import softuni.aggregator.web.annotations.Log;

import java.util.Map;
import java.util.concurrent.Callable;

@Controller
@RequestMapping("/imports")
public class ImportController {

    private final ImportService importService;

    @Autowired
    public ImportController(ImportService importService) {
        this.importService = importService;
    }

    @GetMapping
    public ModelAndView imports(ModelAndView model) {
        model.setViewName("imports");
        return model;
    }

    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ImportsPageVO> getImportsPage(Pageable pageable, @AuthenticationPrincipal User loggedUser) {
        ImportsPageVO importsPageVO = importService.getImportsPage(pageable, loggedUser);
        return new ResponseEntity<>(importsPageVO, HttpStatus.OK);
    }

    @GetMapping(value = "/types", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> getImportTypes() {
        Map<String, String> importTypes = importService.getImportTypes();
        return new ResponseEntity<>(importTypes, HttpStatus.OK);
    }

    @Log
    @PostMapping(value = "/xing", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Callable<Integer> importXingFile(@RequestPart("file") MultipartFile file, @AuthenticationPrincipal User loggedUser) {
        return () -> importService.importCompaniesFromXing(loggedUser, file);
    }

    @Log
    @PostMapping(value = "/orbis", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Callable<Integer> importOrbisFile(@RequestPart("file") MultipartFile file, @AuthenticationPrincipal User loggedUser) {
        return () -> importService.importCompaniesFromOrbis(loggedUser, file);
    }

    @Log
    @PostMapping(value = "/employees", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Callable<Integer> importEmployeesFile(@RequestPart("file") MultipartFile file, @AuthenticationPrincipal User loggedUser) {
        return () -> importService.importEmployees(loggedUser, file);
    }
}
