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
                let dateString = getDateString(exp['generatedOn']);

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

        function getDateString(receivedDate) {
            let utcDate = new Date(receivedDate);

            let year = utcDate.getFullYear();
            let month = utcDate.getMonth();
            let day = utcDate.getDate();
            let hours = utcDate.getHours();
            let minutes = utcDate.getMinutes();

            let date = new Date(Date.UTC(year, month, day, hours, minutes));

            year = date.getFullYear();
            month = pad(date.getMonth());
            day = pad(date.getDate());
            hours = pad(date.getHours());
            minutes = pad(date.getMinutes());

            return `${day}-${month}-${year}\xa0\xa0${hours}:${minutes}`;
        }

        function pad(number) {
            number = number + '';
            return number.length >= 2 ? number : new Array(2 - number.length + 1).join('0') + number;
        }
    });
})();