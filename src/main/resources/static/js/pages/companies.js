(function () {
    $(document).ready(function () {

        attachEvents();

        function attachEvents() {
            $(document).on('click', '.page-item', () => notification.warning("test"))
        }
    });
})();