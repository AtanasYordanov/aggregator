(function () {
    $(document).ready(function () {

        const $tableBody = $('#companies-table tbody');
        const $spinner = $('.spinner-border');
        const $pagination = $('.pagination');
        const $matchesBox = $('.matches-box');
        const $selectIndustry = $('#industry-select');

        let totalCompanies;
        let pagesCount;
        let currentPage = 0;

        attachEvents();
        fetchData();

        function attachEvents() {
            $('#industry-select').on('change', () => fetchCompanies(currentPage));
            $('#export-btn').on('click', exportCompanies)
        }

        function fetchData() {
            fetch('/companies/data?page=1&size=20&sort=industry')
                .then(res => res.json())
                .then(data => {
                    $spinner.hide();
                    renderMajorIndustries(data['majorIndustries']);
                    renderMinorIndustries(data['minorIndustries']);
                    renderCompanies(data['companies']);
                    totalCompanies = data['totalCompaniesCount'];
                    updatesMatches();
                    renderPagination();
                });
        }

        function fetchCompanies(page) {
            const industry = $selectIndustry.val();

            currentPage = page;
            $tableBody.empty();
            $spinner.show();
            fetch(`/companies/page?page=${page}&size=20&sort=industry&industry=${industry}`)
                .then(res => res.json())
                .then(data => {
                    $spinner.hide();
                    renderCompanies(data['companies']);
                    totalCompanies = data['totalCompaniesCount'];
                    updatesMatches();
                    renderPagination();
                });
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
                $tableRow.append($('<td>').text(currentPage * 20 + i + 1));
                $tableRow.append($('<td>').text(company['name']));
                $tableRow.append($('<td>').text(company['industry']));

                $tableRow.append($('<td>')
                    .append($(`<a href="https://${company['website']}">${company['website']}</a>`)));

                $tableRow.append($(`<td class="btn-col"><a href="/company/${company['id']}" 
                                            class="btn btn-outline-secondary btn-sm">View Details</a></td>`));

                $tableBody.append($tableRow);
            });
        }

        function exportCompanies() {
            const industry = $selectIndustry.val();

            fetch(`/export/companies?sort=industry&industry=${industry}`)
                .then(() => notification.success("Successfully generated companies report."))
                .catch(notification.handleError);
        }

        function updatesMatches() {
            pagesCount = totalCompanies % 20 === 0
                ? parseInt(totalCompanies / 20)
                : parseInt(totalCompanies / 20) + 1;
        }

        function renderPagination() {
            $pagination.empty();

            const $firstPageBtn = $(`<li class="page-item">`
                + `<a href="#" class="page-link">First</a>`
                + `</li>`);
            $firstPageBtn.on('click', function (e) {
                e.preventDefault();
                fetchCompanies(0)
            });

            const $lastPageBtn = $(`<li class="page-item">`
                + `<a href="#" class="page-link">Last</a>`
                + `</li>`);
            $lastPageBtn.on('click', function (e) {
                e.preventDefault();
                fetchCompanies(pagesCount - 1)
            });

            const disablePrevBtn = currentPage === 0;
            const $prevBtn = $(`<li class="page-item ${currentPage === 0 ? 'disabled' : ''}">`
                    + `<a href="#" class="page-link">&laquo;</a>`
                    + `</li>`);

            if (!disablePrevBtn) {
                $prevBtn.on('click', function (e) {
                    e.preventDefault();
                    fetchCompanies(currentPage - 1)
                });
            }

            const disableNextBtn = currentPage === pagesCount - 1;
            const $nextBtn = $(`<li class="page-item ${currentPage + 1 === pagesCount ? 'disabled' : ''}">`
                + `<a href="#" class="page-link">&raquo;</a>`
                + `</li>`);

            if (!disableNextBtn) {
                $nextBtn.on('click', function (e) {
                    e.preventDefault();
                    fetchCompanies(currentPage + 1)
                });
            }

            $pagination.append($firstPageBtn);
            $pagination.append($prevBtn);

            let from = Math.max(0, currentPage - 3);
            let to = Math.min(pagesCount - 1, currentPage + 3);
            to = to - from < 6 ? Math.min(from + 6, pagesCount - 1) : to;
            from = to - from < 6 ? Math.max(to - 6, 0) : from;

            for (let i = from; i <= to; i++) {
                const $pageLink = $(`<li class="page-item ${currentPage === i ? 'active' : ''}">`
                    + `<a href="#" class="page-link">${i + 1}</a>`
                    + `</li>`);

                $pageLink.on('click', function(e) {
                    e.preventDefault();
                    fetchCompanies(i)
                });

                $pagination.append($pageLink);
            }

            $pagination.append($nextBtn);
            $pagination.append($lastPageBtn);

            $matchesBox.text('Total matches: ' + totalCompanies);
        }
    });
})();