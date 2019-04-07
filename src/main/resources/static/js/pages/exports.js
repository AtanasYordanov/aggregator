(function () {
    $(document).ready(function () {

        const itemsPerPage = 20;

        const $tableBody = $('#exports-table tbody');
        const $spinner = $('.table-spinner-wrapper');

        let totalExports;
        let currentPage = 0;

        fetchExports(currentPage);

        function fetchExports(page) {
            currentPage = page;
            $tableBody.empty();
            $spinner.show();

            http.get(`/exports/page?page=${page}&size=${itemsPerPage}&sort=generatedOn,desc`
                , (data) => {
                    $spinner.hide();
                    renderExports(data['exports']);
                    totalExports = data['totalItemsCount'];
                    pagination.render(fetchExports, currentPage, totalExports, itemsPerPage);
                }, () => ("Failed to load exports."));
        }

        function renderExports(exports) {
            exports.forEach((exp, i) => {
                let dateString = CustomUtils.getDateString(exp['generatedOn']);

                const $tableRow = $('<tr>');
                $tableRow.append($('<td>').text(currentPage * itemsPerPage + i + 1));
                $tableRow.append($('<td>').text(exp['type']));
                $tableRow.append($('<td>').text(exp['itemsCount']));
                $tableRow.append($('<td>').text(dateString));
                $tableRow.append($(`<td class="btn-col">` +
                    `<a href="/exports/${exp['id']}" class="btn btn-outline-secondary btn-sm">` +
                    `<i class="fa fa-download mr-2" aria-hidden="true"></i>Download</a></td>`));

                $tableBody.append($tableRow);
            });
        }
    });
})();