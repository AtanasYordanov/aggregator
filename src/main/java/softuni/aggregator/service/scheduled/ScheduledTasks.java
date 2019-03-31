package softuni.aggregator.service.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import softuni.aggregator.service.ExportService;

@Service
public class ScheduledTasks {

    private final ExportService exportService;

    @Autowired
    public ScheduledTasks(ExportService exportService) {
        this.exportService = exportService;
    }

    @Scheduled(fixedRate = 21_600_000)
    public void deleteOldExports() {
        exportService.deleteOldExports();
    }
}
