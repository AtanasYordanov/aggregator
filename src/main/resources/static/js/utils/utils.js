let CustomUtils = (() => {

    function getDateString(receivedDate) {
        let utcDate = new Date(receivedDate);

        let year = utcDate.getFullYear();
        let month = utcDate.getMonth();
        let day = utcDate.getDate();
        let hours = utcDate.getHours();
        let minutes = utcDate.getMinutes();

        let date = new Date(Date.UTC(year, month, day, hours, minutes));

        year = date.getFullYear();
        month = pad(date.getMonth() + 1);
        day = pad(date.getDate());
        hours = pad(date.getHours());
        minutes = pad(date.getMinutes());

        return `${day}-${month}-${year}\xa0\xa0${hours}:${minutes}`;
    }

    function pad(number, symbol = '0') {
        number = number + '';
        return number.length >= 2 ? number : new Array(2 - number.length + 1).join(symbol) + number;
    }

    return {
        getDateString,
        pad
    };
})();