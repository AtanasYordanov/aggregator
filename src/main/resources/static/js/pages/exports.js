(function () {
    $(document).ready(function () {

        const itemsPerPage = 20;

        const $tableBody = $('#exports-table tbody');
        const $spinner = $('.table-spinner-wrapper');

        let totalExports;
        let currentPage = 0;

        const inAllExports = window.location.href.includes('admin');

        fetchExports(currentPage);

        function fetchExports(page) {
            currentPage = page;
            $tableBody.empty();
            $spinner.show();

            let url = `/exports/page?page=${page}&size=${itemsPerPage}&sort=generatedOn,desc`;
            url = inAllExports ? `/admin` + url : url;

            http.get(url
                , (data) => {
                    $spinner.hide();
                    renderExports(data['exports']);
                    totalExports = data['totalItemsCount'];
                    pagination.render(fetchExports, currentPage, totalExports, itemsPerPage);
                }, () => $spinner.hide());
        }

        function renderExports(exports) {
            exports.forEach((exp, i) => {
                const $tableRow = $('<tr>');

                const dateString = CustomUtils.getDateString(exp['generatedOn']);

                $tableRow.append($('<td>').text(currentPage * itemsPerPage + i + 1));
                $tableRow.append($('<td>').text(exp['exportName']));
                $tableRow.append($('<td>').text(exp['type']));
                $tableRow.append($('<td>').text(exp['itemsCount']));
                $tableRow.append($('<td>').text(dateString));
                $tableRow.append(inAllExports
                    ? $('<td>').text(exp['userEmail'])
                    : $(`<td class="btn-col">` +
                        `<a href="/exports/${exp['id']}" class="btn btn-outline-secondary btn-sm">` +
                        `<i class="fa fa-download mr-2" aria-hidden="true"></i>Download</a></td>`));

                $tableBody.append($tableRow);
            });
        }
    });
})();