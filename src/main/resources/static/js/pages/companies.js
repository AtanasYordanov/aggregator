(function () {
    $(document).ready(function () {

        attachEvents();

        fetchCompanies();

        function attachEvents() {
            $(document).on('click', '.page-item', () => notification.warning("test"))
        }

        function fetchCompanies() {

        }
    });
})();