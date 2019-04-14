const news = (() => {

    function companiesExportInProgress() {
        return !!sessionStorage.getItem('exporting-companies');
    }

    function employeesExportInProgress() {
        return !!sessionStorage.getItem('exporting-employees');
    }

    setInterval(() => {
        if (sessionStorage.getItem('exporting-companies')
            || sessionStorage.getItem('exporting-employees')) {

            http.get('/news'
                , (data) => {
                    const tasks = data['finishedTasks'];
                    const runningTasks = data['runningTasks'];

                    if ((!tasks || tasks.length === 0) && !runningTasks) {
                        sessionStorage.removeItem('exporting-companies');
                        sessionStorage.removeItem('exporting-employees');
                    }

                    if (tasks && tasks.length > 0) {
                        tasks.forEach(task => {
                            const taskType = task['taskName'];

                            if (taskType === 'companies') {
                                sessionStorage.removeItem('exporting-companies');
                                notification.success(`Successfully exported ${task['itemsCount']} companies`);

                                if (window.location.href.includes('companies')) {
                                    const $exportBtn = $('#export-btn');
                                    $exportBtn.find('.btn-spinner').remove();
                                    $exportBtn.find('.btn-text').text('EXPORT');
                                    $exportBtn.attr('disabled', false);
                                }
                            } else if (taskType === 'employees') {
                                sessionStorage.removeItem('exporting-employees');
                                notification.success(`Successfully exported ${task['itemsCount']} employees`);

                                if (window.location.href.includes('employees')) {
                                    const $exportBtn = $('#export-btn');
                                    $exportBtn.find('.btn-spinner').remove();
                                    $exportBtn.find('.btn-text').text('EXPORT');
                                    $exportBtn.attr('disabled', false);
                                }
                            }
                        });
                    }
                });
        }
    }, 1000);

    return {
        companiesExportInProgress,
        employeesExportInProgress
    }
})();