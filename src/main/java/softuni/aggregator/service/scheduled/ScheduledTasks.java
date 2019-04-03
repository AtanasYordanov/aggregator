package softuni.aggregator.service.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import softuni.aggregator.service.ExportService;
import softuni.aggregator.service.UserService;

@Service
public class ScheduledTasks {

    private final ExportService exportService;
    private final UserService userService;

    @Autowired
    public ScheduledTasks(ExportService exportService, UserService userService) {
        this.exportService = exportService;
        this.userService = userService;
    }

    @Scheduled(initialDelay = 5000, fixedRate = 21_600_000)
    public void deleteOldExports() {
        exportService.deleteOldExports();
    }

    @Scheduled(initialDelay = 15000, fixedRate = 21_600_000)
    public void updateUserStatuses() {
        userService.updateUserStatus();
    }
}
