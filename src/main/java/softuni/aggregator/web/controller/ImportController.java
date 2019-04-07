package softuni.aggregator.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import softuni.aggregator.domain.entities.User;
import softuni.aggregator.domain.model.vo.ImportListVO;
import softuni.aggregator.domain.model.vo.page.ImportsPageVO;
import softuni.aggregator.service.ImportService;

import java.util.List;

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
    public ResponseEntity<ImportsPageVO> getExportsPage(Pageable pageable, @AuthenticationPrincipal User loggedUser) {
        List<ImportListVO> imports = importService.getImportsPage(pageable, loggedUser);
        long importsCount = importService.getImportsCount(loggedUser);

        ImportsPageVO importsPageVO = new ImportsPageVO();
        importsPageVO.setImports(imports);
        importsPageVO.setTotalItemsCount(importsCount);

        return new ResponseEntity<>(importsPageVO, HttpStatus.OK);
    }

    @PostMapping("/xing")
    public ModelAndView importXingFile(ModelAndView model, @RequestParam("file") MultipartFile file,
                                       @AuthenticationPrincipal User loggedUser) {
        importService.importCompaniesFromXing(loggedUser, file);
        model.setViewName("redirect:/home");
        return model;
    }

    @PostMapping("/orbis")
    public ModelAndView importOrbisFile(ModelAndView model, @RequestParam("file") MultipartFile file,
                                        @AuthenticationPrincipal User loggedUser) {
        importService.importCompaniesFromOrbis(loggedUser, file);
        model.setViewName("redirect:/home");
        return model;
    }

    @PostMapping("/employees")
    public ModelAndView importEmployeesFile(ModelAndView model, @RequestParam("file") MultipartFile file,
                                            @AuthenticationPrincipal User loggedUser) {
        importService.importEmployees(loggedUser, file);
        model.setViewName("redirect:/home");
        return model;
    }
}
