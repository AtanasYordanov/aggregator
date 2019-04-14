package softuni.aggregator.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import softuni.aggregator.domain.entities.User;
import softuni.aggregator.domain.model.vo.FinishedTaskVO;
import softuni.aggregator.domain.model.vo.NewsVO;
import softuni.aggregator.service.NewsService;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public ResponseEntity<NewsVO> checkForFinishedTasks(@AuthenticationPrincipal User loggedUser) {
        List<FinishedTaskVO> finishedTasks = newsService.checkForFinishedTasks(loggedUser.getId());
        boolean runningTasks = newsService.checkForRunningTasks(loggedUser.getId());
        NewsVO newsVO = new NewsVO(finishedTasks, runningTasks);
        return new ResponseEntity<>(newsVO, HttpStatus.OK);
    }
}
