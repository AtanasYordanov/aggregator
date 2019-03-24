(function () {
    $(document).ready(function () {

        attachEvents();

        function attachEvents() {
            $(document).on('click', '.page-item', () => console.log("kur"))
        }
        //
        // const $tableBody = $('tbody');
        //
        // function attachEvents() {
        //     $('#show-page-header')
        //         .append($('<div class="radio-buttons">\n' +
        //             '<div id="viruses-btn" class="custom-control custom-radio custom-control-inline">\n' +
        //             '        <input type="radio" id="customRadioInline1" name="customRadioInline1"\n' +
        //             '               class="custom-control-input">\n' +
        //             '        <label class="custom-control-label" for="customRadioInline1">Viruses</label>\n' +
        //             '    </div>\n' +
        //             '    <div id="capitals-btn" class="custom-control custom-radio custom-control-inline">\n' +
        //             '        <input type="radio" id="customRadioInline2" name="customRadioInline1"\n' +
        //             '               class="custom-control-input">\n' +
        //             '        <label class="custom-control-label" for="customRadioInline2">Capitals</label>\n' +
        //             '    </div>\n' +
        //             '</div>'));
        //
        //     $('#viruses-btn')
        //         .on('change', displayViruses);
        //
        //     $('#capitals-btn')
        //         .prop('checked', false)
        //         .on('change', displayCapitals);
        // }
        //
        // function displayViruses() {
        //     $virusesTable.empty();
        //
        //     fetch('http://localhost:8080/viruses')
        //         .then((response) => response.json())
        // .then((data) => {
        //         renderViruses(data);
        // });
        // }
        //
        // function displayCapitals() {
        //     $virusesTable.empty();
        //
        //     fetch('http://localhost:8080/capitals')
        //         .then((response) => response.json())
        // .then((data) => {
        //         renderCapitals(data);
        // });
        // }
        //
        // function renderViruses(viruses) {
        //     $noSelectionText.hide();
        //     $virusesTable.show();
        //
        //     $virusesTable.append($('<thead>\n' +
        //         '<tr>\n' +
        //         '    <th scope="col">#</th>\n' +
        //         '    <th scope="col">Name</th>\n' +
        //         '    <th scope="col">Magnitude</th>\n' +
        //         '    <th scope="col">Released On</th>\n' +
        //         '    <th scope="col"></th>\n' +
        //         '    <th scope="col"></th>\n' +
        //         '</tr>\n' +
        //         '</thead>'));
        //     const $tableBody = $('<tbody>');
        //     $virusesTable.append($tableBody);
        //
        //     viruses.forEach((virus, index) => {
        //         const releasedDate = new Date(Date.parse(virus['releasedOn']));
        //     const releasedDateString = getDateAsString(releasedDate);
        //
        //     $($tableBody)
        //         .append($('<tr>')
        //             .append($('<th scope="row">').text(index + 1))
        //             .append($('<td>').text(virus['name']))
        //             .append($('<td>').text(virus['magnitude']))
        //             .append($('<td>').text(releasedDateString))
        //             .append($('<td>')
        //                 .append($(`<a class="btn btn-outline-dark" href="/viruses/edit/${virus.id}">`).text('Edit')))
        //             .append($('<td>')
        //                 .append($(`<a class="btn btn-outline-dark" href="/viruses/delete/${virus.id}">`).text('Delete')))
        //         )
        // });
        // }
        //
        // function renderCapitals(capitals) {
        //     $noSelectionText.hide();
        //     $virusesTable.show();
        //
        //     capitals.forEach((capital, index) => {
        //         $($tableBody)
        //         .append($('<tr>')
        //             .append($('<th scope="row">').text(index + 1))
        //             .append($('<td>').text(capital['name']))
        //             .append($('<td>').text(capital['latitude']))
        //             .append($('<td>').text(capital['longitude']))
        //         )
        //     });
        // }
        //
        // function getDateAsString(date) {
        //     const monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun",
        //         "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        //     ];
        //
        //     let dayOfMonth = date.getDate() + '';
        //     dayOfMonth = dayOfMonth.length === 1 ? '0' + dayOfMonth : dayOfMonth;
        //
        //     let month = monthNames[date.getMonth()];
        //     let year = date.getFullYear();
        //
        //     return `${dayOfMonth}-${month}-${year}`;
        // }
    });
})();