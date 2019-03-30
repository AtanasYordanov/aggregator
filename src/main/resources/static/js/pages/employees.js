(function () {
    $(document).ready(function () {

        const itemsPerPage = 20;

        const $tableBody = $('#employees-table tbody');
        const $spinner = $('.table-spinner-wrapper');
        const $exportBtn = $('#export-btn');

        let totalExports;
        let currentPage = 0;

        attachEvents();
        fetchEmployees(currentPage);

        function attachEvents() {
            $exportBtn.on('click', exportEmployees)
        }

        function fetchEmployees(page) {
            currentPage = page;
            $tableBody.empty();
            $spinner.show();
            fetch(`/employees/page?page=${page}&size=${itemsPerPage}`)
                .then(res => res.json())
                .then(data => {
                    $spinner.hide();
                    renderEmployees(data['employees']);
                    totalExports = data['totalItemsCount'];
                    pagination.render(fetchEmployees, currentPage, totalExports, itemsPerPage);
                })
                .catch(notification.handleError);
        }

        function renderEmployees(employees) {
            employees.forEach((employee, i) => {
                const $tableRow = $('<tr>');
                $tableRow.append($('<td>').text(currentPage * itemsPerPage + i + 1));
                $tableRow.append($('<td>').text(employee['fullName']));
                $tableRow.append($('<td>').text(employee['email']));
                $tableRow.append($('<td>').text(employee['company']));
                $tableRow.append($(`<td class="btn-col"><a href="/employees/${employee['id']}" 
                                            class="btn btn-outline-secondary btn-sm">View Details</a></td>`));

                $tableBody.append($tableRow);
            });
        }

        function exportEmployees() {
            const $buttonSpinner = $(`<span class="btn-spinner spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>`);
            $exportBtn.prepend($buttonSpinner);
            $exportBtn.find('.btn-text').text('EXPORTING');
            $exportBtn.attr('disabled', true);

            fetch(`/exports/employees`)
                .then((res) => {
                    $buttonSpinner.remove();
                    $exportBtn.find('.btn-text').text('EXPORT');
                    $exportBtn.attr('disabled', false);

                    let status = res.status;
                    res.json().then(count => {
                        if (status === 200) {
                            notification.success(`Successfully exported ${count} employees.`);
                        } else {
                            notification.error("Failed to generate report.");
                        }
                    });
                })
                .catch(notification.handleError);
        }
    });
})();