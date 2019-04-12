(function () {
    $(document).ready(function () {

        const itemsPerPage = 20;

        const $tableBody = $('#employees-table tbody');
        const $spinner = $('.table-spinner-wrapper');
        const $exportBtn = $('#export-btn');
        const $filterBtn = $('#filter-btn');

        const $industrySelect = $('#industry-select');
        const $minEmployeesCountField = $('#min-employee-count');
        const $maxEmployeesCountField = $('#max-employee-count');
        const $includeCompaniesWithNoDataSelect = $('#include-companies-with-no-data-select');
        const $yearFoundField = $('#year-found-input');
        const $countryField = $('#country-input');
        const $cityField = $('#city-input');

        let totalEmployees;
        let currentPage = 0;

        attachEvents();
        fetchData();

        function attachEvents() {
            $industrySelect.on('change', () => fetchEmployees(currentPage));
            $includeCompaniesWithNoDataSelect.on('change', () => fetchEmployees(currentPage));
            attachOnEnterPressEvent($minEmployeesCountField);
            attachOnEnterPressEvent($maxEmployeesCountField);
            attachOnEnterPressEvent($yearFoundField);
            attachOnEnterPressEvent($countryField);
            attachOnEnterPressEvent($cityField);

            $exportBtn.on('click', displayExportModal);
            $filterBtn.on('click', () => fetchEmployees(currentPage));

            function attachOnEnterPressEvent($input) {
                $input.keypress(function (event) {
                    const keycode = (event.keyCode ? event.keyCode : event.which);
                    if (keycode === 13) {
                        fetchEmployees(currentPage);
                    }
                });
            }
        }

        function fetchData() {
            http.get(`/employees/data` + buildQueryString()
                , (data) => {
                    $spinner.hide();
                    renderMainIndustries(data['mainIndustries']);
                    renderSubIndustries(data['subIndustries']);
                    renderEmployees(data['employees']);
                    totalEmployees = data['totalItemsCount'];
                    pagination.render(fetchEmployees, currentPage, totalEmployees, itemsPerPage);
                }
                , () => $spinner.hide());
        }

        function fetchEmployees(page) {
            currentPage = page;
            $tableBody.empty();
            $spinner.show();

            http.get(`/employees/page` + buildQueryString()
                , (data) => {
                    $spinner.hide();
                    renderEmployees(data['employees']);
                    totalEmployees = data['totalItemsCount'];
                    pagination.render(fetchEmployees, currentPage, totalEmployees, itemsPerPage);
                }
                , () => $spinner.hide());
        }

        function renderMainIndustries(industries) {
            const $industries = $('#main-industries');
            industries.forEach(industry => {
                $industries.append($(`<option>`).val('Main:' + industry).text(industry));
            });
        }

        function renderSubIndustries(industries) {
            const $industries = $('#sub-industries');
            industries.forEach(industry => {
                $industries.append($(`<option>`).val('Sub:' + industry).text(industry));
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

        function displayExportModal() {
            $('#modal').remove();

            const $modal = $(modal.getModalTemplate('Export employees', 'CANCEL', 'EXPORT'));

            const $exportNameImport = $(`
                    <div class="form-group">
                        <label for="export-name w-100">Export name</label>
                        <input type="text" name="exportName" class="form-control w-100" id="export-name"
                               placeholder="Export name">
                    </div>
                `);

            const $includeCompaniesSelect = $(`
                    <div class="form-group form-check">
                        <input type="checkbox" class="form-check-input" id="include-companies-select">
                        <label class="form-check-label" for="include-companies-select">Include companies</label>
                    </div>`);

            const $exportNameInput = $exportNameImport.find('#export-name');
            $exportNameInput.val(CustomUtils.buildExportName($industrySelect.val()));

            $modal.find('#confirm-btn').on('click', () => exportEmployees($modal, $exportNameInput, $includeCompaniesSelect));

            $modal.find('.modal-body').append($exportNameImport);
            $modal.find('.modal-body').append($includeCompaniesSelect);

            $('body').append($modal);
            $modal.modal();
        }

        function exportEmployees($modal, $exportNameInput, $includeCompaniesSelect) {
            const $buttonSpinner = $(`<span class="btn-spinner spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>`);
            $exportBtn.prepend($buttonSpinner);
            $exportBtn.find('.btn-text').text('EXPORTING');
            $exportBtn.attr('disabled', true);

            const exportName = $exportNameInput.val();
            const includeCompanies = $includeCompaniesSelect.find('#include-companies-select').prop("checked");

            http.post(`/exports/employees` + buildQueryString()
                , {exportName, includeCompanies}
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
                });

            $modal.modal('hide');
            $modal.detach();
        }

        function buildQueryString() {
            const industry = $industrySelect.val();
            const minEmployeesCount = $minEmployeesCountField.val();
            const maxEmployeesCount = $maxEmployeesCountField.val();
            const yearFound = $yearFoundField.val();
            const includeCompaniesWithNoEmployeeData = $includeCompaniesWithNoDataSelect.prop("checked");
            const country = $countryField.val();
            const city = $cityField.val();

            let url = `?page=${currentPage}&size=${itemsPerPage}`;
            url += `&industry=${industry}&minEmployeesCount=${minEmployeesCount}&maxEmployeesCount=${maxEmployeesCount}`;
            url += `&includeCompaniesWithNoEmployeeData=${includeCompaniesWithNoEmployeeData}&yearFound=${yearFound}`;
            url += `&country=${country}&city=${city}`;
            return url;
        }
    });
})();