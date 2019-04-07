(function () {
    $(document).ready(function () {

        const itemsPerPage = 20;

        const $tableBody = $('#imports-table tbody');
        const $spinner = $('.table-spinner-wrapper');
        const $importBtn = $('#import-btn');

        let totalImports;
        let currentPage = 0;

        attachEvents();
        fetchImports(currentPage);

        function attachEvents() {
            $importBtn.on('click', () => displayImportModal())
        }

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
                const $tableRow = $('<tr>');

                const dateString = CustomUtils.getDateString(exp['date']);

                $tableRow.append($('<td>').text(currentPage * itemsPerPage + i + 1));
                $tableRow.append($('<td>').text(exp['type']));
                $tableRow.append($('<td>').text(exp['totalItemsCount']));
                $tableRow.append($('<td>').text(exp['newEntriesCount']));
                $tableRow.append($('<td>').text(dateString));
                $tableRow.append($('<td>').text(exp['userEmail']));

                $tableBody.append($tableRow);
            });
        }

        function displayImportModal() {
            $('#modal').remove();

            const $modal = $(modal.getModalTemplate('Import data', 'CANCEL', 'IMPORT'));

            const $selectImport = buildSelectImportTypeBox();
            const $fileInputBox = buildFileInput();

            $modal.find('#confirm-btn').on('click', () => importFile($modal, $selectImport));

            $modal.find('.modal-body').append($selectImport);
            $modal.find('.modal-body').append($fileInputBox);

            $('body').append($modal);
            $modal.modal();
        }

        function buildSelectImportTypeBox() {
            const $selectImport = $(`
                    <div class="form-row">
                        <div class="form-group col-lg-6">
                            <label for="import-select"> </label>
                            <select id="import-select" class="form-control">
                                <option disabled selected>Select Import Type</option>
                            </select>
                        </div>
                    </div>
                `);

            http.get('/imports/types'
                , (importTypes) => {
                    const $options = $selectImport.find('#import-select');
                    Object.keys(importTypes)
                        .forEach(key => $options.append($(`<option>`).val(key).text(importTypes[key])))
                }
                , () => notification.error("Failed to load imports."));
            return $selectImport;
        }

        function buildFileInput() {
            const $fileInputBox = $(`
                    <div class="custom-file col-lg-8 mr-4">
                        <input type="file" name="file" readonly class="custom-file-input" id="file-input"
                               accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"/>
                        <label class="custom-file-label" for="file-input">Choose file</label>
                    </div>
                `);

            $fileInputBox.find('#file-input').on('change', () => {
                const file = document.getElementById('file-input').files[0];
                $('.custom-file-label').text(file.name);
            });
            return $fileInputBox;
        }

        function importFile($modal, $selectImport) {
            const importType = $selectImport.find('#import-select').val();
            const file = document.getElementById('file-input').files[0];
            const formData = new FormData();
            formData.append('file', file);

            const $buttonSpinner = $(`<span class="btn-spinner spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>`);
            $importBtn.prepend($buttonSpinner);
            $importBtn.find('.btn-text').text('IMPORTING');
            $importBtn.attr('disabled', true);

            document.getElementById('file-input').value = "";

            http.uploadFile(`/imports/${importType}`, formData
                , (count) => {
                    $buttonSpinner.remove();
                    $importBtn.find('.btn-text').text('NEW IMPORT');
                    $importBtn.attr('disabled', false);
                    notification.success(`Successfully imported ${count} items.`);
                }
                , () => {
                    $buttonSpinner.remove();
                    $importBtn.find('.btn-text').text('NEW IMPORT');
                    $importBtn.attr('disabled', false);
                    notification.error('Import failed.');
                });

            $modal.modal('hide');
            $modal.detach();
        }
    });
})();