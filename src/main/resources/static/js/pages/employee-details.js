(function () {
    $(document).ready(function () {

        $('#remove-btn').on('click', displayConfirmationModal);


        function displayConfirmationModal() {
            $('#modal').remove();

            const $modal = $(modal.getModalTemplate('Remove employee', 'CANCEL', 'REMOVE'));

            const $confirmationText = $('<p>Are you sure you want to remove this employee?</p>');

            $modal.find('#confirm-btn').on('click', () => deleteEmployee($modal));
            $modal.find('.modal-body').append($confirmationText);

            $('body').append($modal);
            $modal.modal();
        }

        function deleteEmployee($modal) {
            const url = window.location.href;
            const employeeId = url.substring(url.lastIndexOf('/') + 1);

            http.del(`/employees/delete/${employeeId}`
                , () => window.history.back());

            $modal.modal('hide');
            $modal.detach();
        }
    });
})();