(function () {
    $(document).ready(function () {

        $('#remove-btn').on('click', displayConfirmationModal);


        function displayConfirmationModal() {
            $('#modal').remove();

            const $modal = $(modal.getModalTemplate('Remove company', 'CANCEL', 'REMOVE'));

            const $confirmationText = $('<p>Are you sure you want to remove this company?</p>');

            $modal.find('#confirm-btn').on('click', () => deleteCompany($modal));
            $modal.find('.modal-body').append($confirmationText);

            $('body').append($modal);
            $modal.modal();
        }

        function deleteCompany($modal) {
            const url = window.location.href;
            const companyId = url.substring(url.lastIndexOf('/') + 1);

            http.del(`/companies/delete/${companyId}`
                , () => window.history.back());

            $modal.modal('hide');
            $modal.detach();
        }
    });
})();