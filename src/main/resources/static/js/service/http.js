let http = (() => {

    function get(url, onSuccess, onError) {
        fetch(url)
            .then(res => {
                if (!res.status >= 400) {
                    onError();
                    return;
                }
                res.json().then((data) => {
                    onSuccess(data);
                }).catch(() => onError());
            })
            .catch(notification.handleError);
    }

    return {
        get
    };
})();