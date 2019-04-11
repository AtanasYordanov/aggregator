(function () {
    $(document).ready(function () {

        const itemsPerPage = 20;

        const $tableBody = $('#employees-table tbody');
        const $spinner = $('.table-spinner-wrapper');
        const $exportBtn = $('#export-btn');

        let totalEmployees;
        let currentPage = 0;
        let selectedIndustry = 'all';

        attachEvents();
        fetchData();

        function attachEvents() {
            $('#industry-select').on('change', (e) => {
                selectedIndustry = e.target.value;
                fetchEmployees(currentPage);
            });
            $exportBtn.on('click', exportEmployees)
        }

        function fetchData() {
            http.get(`/employees/data?page=0&size=${itemsPerPage}&industry=${selectedIndustry}`
                , (data) => {
                    $spinner.hide();
                    renderMajorIndustries(data['majorIndustries']);
                    renderMinorIndustries(data['minorIndustries']);
                    renderEmployees(data['employees']);
                    totalEmployees = data['totalItemsCount'];
                    pagination.render(fetchEmployees, currentPage, totalEmployees, itemsPerPage);
                }
                , () => notification.error("Failed to load the companies catalog."));
        }

        function fetchEmployees(page) {
            currentPage = page;
            $tableBody.empty();
            $spinner.show();

            http.get(`/employees/page?page=${page}&size=${itemsPerPage}&industry=${selectedIndustry}`
                , (data) => {
                    $spinner.hide();
                    renderEmployees(data['employees']);
                    totalEmployees = data['totalItemsCount'];
                    pagination.render(fetchEmployees, currentPage, totalEmployees, itemsPerPage);
                }
                , () => notification.error("Failed to load the employees catalog."));
        }

        function renderMajorIndustries(industries) {
            const $industries = $('#major-industries');
            industries.forEach(industry => {
                $industries.append($(`<option>`).val('Maj:' + industry).text(industry));
            });
        }

        function renderMinorIndustries(industries) {
            const $industries = $('#minor-industries');
            industries.forEach(industry => {
                $industries.append($(`<option>`).val('Min:' + industry).text(industry));
            });
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

            http.get(`/exports/employees`
                , (count) => {
                    $buttonSpinner.remove();
                    $exportBtn.find('.btn-text').text('EXPORT');
                    $exportBtn.attr('disabled', false);
                    notification.success(`Successfully exported ${count} employees.`);
                }
                , () => {
                    $buttonSpinner.remove();
                    $exportBtn.find('.btn-text').text('EXPORT');
                    $exportBtn.attr('disabled', false);
                    notification.error("Failed to generate report.");
                });
        }
    });
})();