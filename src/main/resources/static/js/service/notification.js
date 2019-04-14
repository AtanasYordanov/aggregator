const notification = (() => {

    const container = $('#notification-container');
    const notificationTimeOut = 7000;

    function info(message) {
        displayMessage('info', message);
    }

    function success(message) {
        displayMessage('success', message)
    }

    function warning(message) {
        displayMessage('warning', message);
    }

    function error(message) {
        displayMessage('danger', message)
    }

    function displayMessage(type, message) {
        let $alert = $(`<div class="alert alert-${type}" role="alert">${message}</div>`);
        $alert.on('click', () => $alert.fadeOut());
        $alert.hide();

        container.append($alert);
        $alert.fadeIn();

        setTimeout(() => $alert.fadeOut(), notificationTimeOut);
    }

    function handleError(reason) {
        error(reason.responseJSON.description);
    }

    return {
        info,
        success,
        warning,
        error,
        handleError
    };
})();