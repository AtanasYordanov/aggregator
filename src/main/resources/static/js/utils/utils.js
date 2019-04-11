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

    function buildExportName(selectedIndustry) {
        let name;
        if (selectedIndustry === 'all') {
            name = 'All_Industries'
        } else {
            name = selectedIndustry.substring(4);
            while(name.includes(" ")) {
                name = name.replace(/\s+/, "_");
            }
        }

        const date = new Date();

        let year = pad(date.getFullYear());
        let month = pad(date.getMonth() + 1);
        let day = pad(date.getDate());
        let hours = pad(date.getHours());
        let minutes = pad(date.getMinutes());
        let seconds = pad(date.getSeconds());

        return `${name}_${day}-${month}-${year}_${hours}-${minutes}-${seconds}`;
    }

    function pad(number, symbol = '0') {
        number = number + '';
        return number.length >= 2 ? number : new Array(2 - number.length + 1).join(symbol) + number;
    }

    return {
        getDateString,
        buildExportName
    };
})();