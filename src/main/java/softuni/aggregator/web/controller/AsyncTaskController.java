package softuni.aggregator.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import softuni.aggregator.domain.entities.User;
import softuni.aggregator.domain.model.AsyncTask;
import softuni.aggregator.domain.model.TaskInfoVO;
import softuni.aggregator.service.AsyncTaskService;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class AsyncTaskController {

    private final AsyncTaskService asyncTaskService;

    @Autowired
    public AsyncTaskController(AsyncTaskService asyncTaskService) {
        this.asyncTaskService = asyncTaskService;
    }

    @GetMapping
    public ResponseEntity<TaskInfoVO> checkForFinishedTasks(@AuthenticationPrincipal User loggedUser) {
        List<AsyncTask> finishedTasks = asyncTaskService.getFinishedTasks(loggedUser.getId());
        boolean runningTasks = asyncTaskService.checkForRunningTasks(loggedUser.getId());
        TaskInfoVO taskInfoVO = new TaskInfoVO(finishedTasks, runningTasks);
        return new ResponseEntity<>(taskInfoVO, HttpStatus.OK);
    }
}
