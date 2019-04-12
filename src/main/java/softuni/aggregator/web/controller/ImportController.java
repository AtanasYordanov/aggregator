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
import softuni.aggregator.domain.model.vo.ImportListVO;
import softuni.aggregator.domain.model.vo.page.ImportsPageVO;
import softuni.aggregator.service.ImportService;
import softuni.aggregator.service.excel.reader.imports.ImportType;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

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
        List<ImportListVO> imports = importService.getImportsPage(pageable, loggedUser);
        long importsCount = importService.getImportsCountForUser(loggedUser);

        ImportsPageVO importsPageVO = new ImportsPageVO();
        importsPageVO.setImports(imports);
        importsPageVO.setTotalItemsCount(importsCount);

        return new ResponseEntity<>(importsPageVO, HttpStatus.OK);
    }

    @GetMapping(value = "/types", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> getImportTypes() {
        Map<String, String> importTypes = Arrays.stream(ImportType.values())
                .collect(Collectors.toMap(
                        ImportType::getEndpoint
                        , ImportType::toString
                        , (u, v) -> {
                            throw new IllegalStateException(String.format("Duplicate key %s", u));
                        }, LinkedHashMap::new));

        return new ResponseEntity<>(importTypes, HttpStatus.OK);
    }

    @PostMapping(value = "/xing", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Callable<Integer> importXingFile(@RequestPart("file") MultipartFile file, @AuthenticationPrincipal User loggedUser) {
        return () -> importService.importCompaniesFromXing(loggedUser, file);
    }

    @PostMapping(value = "/orbis", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Callable<Integer> importOrbisFile(@RequestPart("file") MultipartFile file, @AuthenticationPrincipal User loggedUser) {
        return () -> importService.importCompaniesFromOrbis(loggedUser, file);
    }

    @PostMapping(value = "/employees", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Callable<Integer> importEmployeesFile(@RequestPart("file") MultipartFile file, @AuthenticationPrincipal User loggedUser) {
        return () -> importService.importEmployees(loggedUser, file);
    }
}
