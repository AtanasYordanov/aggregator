(function () {
    $(document).ready(function () {

        const itemsPerPage = 20;

        const $tableBody = $('#imports-table tbody');
        const $spinner = $('.table-spinner-wrapper');

        let totalImports;
        let currentPage = 0;

        fetchImports(currentPage);

        function fetchImports(page) {
            currentPage = page;
            $tableBody.empty();
            $spinner.show();

            http.get(`/imports/page?page=${page}&size=${itemsPerPage}&sort=date,desc`
                , (data) => {
                    $spinner.hide();
                    renderImports(data['imports']);
                    totalImports = data['totalItemsCount'];
                    pagination.render(fetchImports, currentPage, totalImports, itemsPerPage);
                }, () => ("Failed to load imports."));
        }

        function renderImports(imports) {
            imports.forEach((exp, i) => {
                let dateString = getDateString(exp['date']);

                const $tableRow = $('<tr>');
                $tableRow.append($('<td>').text(currentPage * itemsPerPage + i + 1));
                $tableRow.append($('<td>').text(exp['type']));
                $tableRow.append($('<td>').text(exp['totalItemsCount']));
                $tableRow.append($('<td>').text(exp['newEntriesCount']));
                $tableRow.append($('<td>').text(dateString));
                $tableRow.append($('<td>').text(exp['userEmail']));

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