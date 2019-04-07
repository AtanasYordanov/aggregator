(function () {
    $(document).ready(function () {

        const itemsPerPage = 20;

        const $tableBody = $('#companies-table tbody');
        const $spinner = $('.table-spinner-wrapper');
        const $exportBtn = $('#export-btn');

        let totalCompanies;
        let currentPage = 0;
        let selectedIndustry = 'all';

        attachEvents();
        fetchData();

        function attachEvents() {
            $('#industry-select').on('change', (e) => {
                selectedIndustry = e.target.value;
                fetchCompanies(currentPage);
            });
            $exportBtn.on('click', exportCompanies)
        }

        function fetchData() {
            http.get(`/companies/data?page=0&size=${itemsPerPage}&sort=industry`
                , (data) => {
                    $spinner.hide();
                    renderMajorIndustries(data['majorIndustries']);
                    renderMinorIndustries(data['minorIndustries']);
                    renderCompanies(data['companies']);
                    totalCompanies = data['totalItemsCount'];
                    pagination.render(fetchCompanies, currentPage, totalCompanies, itemsPerPage);
                }
                , () => notification.error("Failed to load the companies catalog."));
        }

        function fetchCompanies(page) {
            currentPage = page;
            $tableBody.empty();
            $spinner.show();

            http.get(`/companies/page?page=${page}&size=${itemsPerPage}&sort=industry&industry=${selectedIndustry}`
                , (data) => {
                    $spinner.hide();
                    renderCompanies(data['companies']);
                    totalCompanies = data['totalItemsCount'];
                    pagination.render(fetchCompanies, page, totalCompanies, itemsPerPage);
                }
                , () => notification.error("Failed to load companies."));
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

        function renderCompanies(companies) {
            companies.forEach((company, i) => {
                const $tableRow = $('<tr>');

                $tableRow.append($('<td>').text(currentPage * itemsPerPage + i + 1));
                $tableRow.append($('<td>').text(company['name']));
                $tableRow.append($('<td>').text(company['industry']));
                $tableRow.append($('<td>')
                    .append($(`<a href="https://${company['website']}">${company['website']}</a>`)));

                $tableRow.append($(`<td class="btn-col"><a href="/companies/${company['id']}" 
                                            class="btn btn-outline-secondary btn-sm">View Details</a></td>`));

                $tableBody.append($tableRow);
            });
        }

        function exportCompanies() {
            const $buttonSpinner = $(`<span class="btn-spinner spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>`);
            $exportBtn.prepend($buttonSpinner);
            $exportBtn.find('.btn-text').text('EXPORTING');
            $exportBtn.attr('disabled', true);

            http.get(`/exports/companies?sort=industry&industry=${selectedIndustry}`
                , (count) => {
                    $buttonSpinner.remove();
                    $exportBtn.find('.btn-text').text('EXPORT');
                    $exportBtn.attr('disabled', false);
                    notification.success(`Successfully exported ${count} companies.`);
                }
                , () => {
                    $buttonSpinner.remove();
                    $exportBtn.find('.btn-text').text('EXPORT');
                    $exportBtn.attr('disabled', false);
                    notification.error("Failed to generate report.")
                });
        }
    });
})();