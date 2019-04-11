let http = (() => {

    function get(url, onSuccess, onError) {
        const options = {
            method: "GET",
        };

        return execute(url, options, onSuccess, onError);
    }

    function post(url, data, onSuccess, onError) {

        const options = {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data),
        };

        return execute(url, options, onSuccess, onError);
    }

    function uploadFile(url, data, onSuccess, onError) {

        const options = {
            method: "POST",
            body: data,
        };

        return execute(url, options, onSuccess, onError);
    }

    function execute(url, options, onSuccess, onError) {
        return fetch(url, options)
            .then(res => {
                if (res.status >= 400) {
                    res.json().then((e) => {
                        notification.error(e.message);
                    }).catch(() => onError());
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
        get,
        post,
        uploadFile
    };
})();