(function () {
    $(document).ready(function () {

        const itemsPerPage = 20;

        const $tableBody = $('#companies-table tbody');
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


        let totalCompanies;
        let currentPage = 0;

        attachEvents();
        fetchData();
        checkForExports();

        function checkForExports() {
            if (news.companiesExportInProgress()) {
                const $buttonSpinner = $(`<span class="btn-spinner spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>`);
                $exportBtn.prepend($buttonSpinner);
                $exportBtn.find('.btn-text').text('EXPORTING');
                $exportBtn.attr('disabled', true);
            }
        }

        function attachEvents() {
            $industrySelect.on('change', () => fetchCompanies(currentPage));
            $includeCompaniesWithNoDataSelect.on('change', () => fetchCompanies(currentPage));
            attachOnEnterPressEvent($minEmployeesCountField);
            attachOnEnterPressEvent($maxEmployeesCountField);
            attachOnEnterPressEvent($yearFoundField);
            attachOnEnterPressEvent($countryField);
            attachOnEnterPressEvent($cityField);

            $exportBtn.on('click', displayExportModal);
            $filterBtn.on('click', () => fetchCompanies(currentPage));

            function attachOnEnterPressEvent($input) {
                $input.keypress(function (event) {
                    const keycode = (event.keyCode ? event.keyCode : event.which);
                    if (keycode === 13) {
                        fetchCompanies(currentPage);
                    }
                });
            }
        }

        function fetchData() {
            http.get(`/companies/data` + buildQueryString()
                , (data) => {
                    $spinner.hide();
                    renderMainIndustries(data['mainIndustries']);
                    renderSubIndustries(data['subIndustries']);
                    renderCompanies(data['companies']);
                    totalCompanies = data['totalItemsCount'];
                    pagination.render(fetchCompanies, currentPage, totalCompanies, itemsPerPage);
                }
                , () => $spinner.hide());
        }

        function fetchCompanies(page) {
            currentPage = page;
            $tableBody.empty();
            $spinner.show();

            http.get(`/companies/page` + buildQueryString()
                , (data) => {
                    $spinner.hide();
                    renderCompanies(data['companies']);
                    totalCompanies = data['totalItemsCount'];
                    pagination.render(fetchCompanies, page, totalCompanies, itemsPerPage);
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

        function renderCompanies(companies) {
            companies.forEach((company, i) => {
                const $tableRow = $('<tr>');

                $tableRow.append($('<td>').text(currentPage * itemsPerPage + i + 1));
                $tableRow.append($('<td>').text(company['name'] || 'n/a'));
                $tableRow.append($('<td>').text(company['industry'] || 'n/a'));
                $tableRow.append($('<td>').text(company['employeesCount'] || 'n/a'));
                $tableRow.append($('<td>').text(company['yearFound'] || 'n/a'));
                $tableRow.append($('<td>')
                    .append($(`<a href="https://${company['website']}">${company['website']}</a>`)));

                $tableRow.append($(`<td class="btn-col"><a href="/companies/${company['id']}" 
                                            class="btn btn-outline-secondary btn-sm">View Details</a></td>`));

                $tableBody.append($tableRow);
            });
        }

        function displayExportModal() {
            $('#modal').remove();

            const $modal = $(modal.getModalTemplate('Export companies', 'CANCEL', 'EXPORT'));

            const $exportNameImport = $(`
                    <div class="form-group">
                        <label for="export-name w-100">Export name</label>
                        <input type="text" name="exportName" class="form-control w-100" id="export-name"
                               placeholder="Export name">
                    </div>
                `);

            const $exportNameInput = $exportNameImport.find('#export-name');
            $exportNameInput.val(CustomUtils.buildExportName($industrySelect.val()));

            $modal.find('#confirm-btn').on('click', () => exportCompanies($modal, $exportNameInput));

            $modal.find('.modal-body').append($exportNameImport);

            $('body').append($modal);
            $modal.modal();
        }

        function exportCompanies($modal, $exportNameInput) {
            const $buttonSpinner = $(`<span class="btn-spinner spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>`);
            $exportBtn.prepend($buttonSpinner);
            $exportBtn.find('.btn-text').text('EXPORTING');
            $exportBtn.attr('disabled', true);

            const exportName = $exportNameInput.val();

            sessionStorage.setItem('exporting-companies', 'true');
            http.post(`/exports/companies` + buildQueryString(), {exportName}
                , () => { }
                , () => {
                    $buttonSpinner.remove();
                    sessionStorage.removeItem('exporting-companies');
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

            let url = `?page=${currentPage}&size=${itemsPerPage}&sort=industry`;
            url += `&industry=${industry}&minEmployeesCount=${minEmployeesCount}&maxEmployeesCount=${maxEmployeesCount}`;
            url += `&includeCompaniesWithNoEmployeeData=${includeCompaniesWithNoEmployeeData}&yearFound=${yearFound}`;
            url += `&country=${country}&city=${city}`;
            return url;
        }
    });
})();