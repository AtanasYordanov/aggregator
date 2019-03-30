(function () {
    $(document).ready(function () {

        const itemsPerPage = 20;

        const $tableBody = $('#exports-table tbody');
        const $spinner = $('.table-spinner-wrapper');

        let totalExports;
        let currentPage = 0;

        fetchData();

        function fetchData() {
            fetch(`/exports/page?page=0&size=${itemsPerPage}&sort=generatedOn,desc`)
                .then(res => res.json())
                .then(data => {
                    $spinner.hide();
                    renderExports(data['exports']);
                    totalExports = data['totalItemsCount'];
                    pagination.render(fetchExports, currentPage, totalExports, itemsPerPage);
                })
                .catch(notification.handleError);
        }

        function fetchExports(page) {
            currentPage = page;
            $tableBody.empty();
            $spinner.show();
            fetch(`/exports/page?page=${page}&size=${itemsPerPage}&sort=generatedOn,desc`)
                .then(res => res.json())
                .then(data => {
                    $spinner.hide();
                    renderExports(data['exports']);
                    totalExports = data['totalItemsCount'];
                    pagination.render(fetchExports, currentPage, totalExports, itemsPerPage);
                })
                .catch(notification.handleError);
        }

        function renderExports(exports) {
            exports.forEach((exp, i) => {
                let dateString = getDateString(exp['generatedOn']);

                const $tableRow = $('<tr>');
                $tableRow.append($('<td>').text(currentPage * itemsPerPage + i + 1));
                $tableRow.append($('<td>').text(exp['type']));
                $tableRow.append($('<td>').text(exp['itemsCount']));
                $tableRow.append($('<td>').text(dateString));
                $tableRow.append($(`<td class="btn-col"><a href="/exports/${exp['id']}" 
                                            class="btn btn-outline-secondary btn-sm">Download</a></td>`));

                $tableBody.append($tableRow);
            });
        }

        function getDateString(receivedDate) {
            let utcDate = new Date(receivedDate);
            let year = utcDate.getFullYear();
            let month = utcDate.getMonth();
            let day = utcDate.getDate();

            let date = new Date(Date.UTC(year, month, day));

            year = date.getFullYear();
            month = pad(date.getMonth());
            day = pad(date.getDate());

            return `${day}-${month}-${year}`;
        }

        function pad(number) {
            number = number + '';
            return number.length >= 2 ? number : new Array(2 - number.length + 1).join('0') + number;
        }
    });
})();