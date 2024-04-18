

function parseStringToDate(string) {
    const parts = string.split('/');

    const date = new Date(Date.UTC(parseInt(parts[2]), parseInt(parts[1]) - 1, parseInt(parts[0])));
    return date;
}

function parseDateToString(dateString) {
    var parts = dateString.split(' ');

    var dateParts = parts[0].split('-');
    var year = parseInt(dateParts[0]);
    var month = parseInt(dateParts[1]) - 1;
    var day = parseInt(dateParts[2]);

    var timeParts = parts[1] ? parts[1].split(':') : [0, 0, 0];
    var hours = parseInt(timeParts[0]);
    var minutes = parseInt(timeParts[1]);
    var seconds = parseInt(timeParts[2]);

    var date = new Date(year, month, day, hours, minutes, seconds);

    var formattedDay = date.getDate().toString().padStart(2, '0');
    var formattedMonth = (date.getMonth() + 1).toString().padStart(2, '0');
    var formattedYear = date.getFullYear();

    return formattedDay + '/' + formattedMonth + '/' + formattedYear;
}

module.exports = {
    parseStringToDate,
    parseDateToString
}