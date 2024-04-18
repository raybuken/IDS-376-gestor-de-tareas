

function parseStringToDate(string) {
    const parts = string.split('/');

    const date = new Date(Date.UTC(parseInt(parts[2]), parseInt(parts[1]) - 1, parseInt(parts[0])));
    return date;
}

function parseDateToString(date) {
    const day = date.getUTCDate();
    const month = date.getUTCMonth() + 1; 
    const year = date.getUTCFullYear();

    const formattedDay = (day < 10 ? '0' : '') + day;
    const formattedMonth = (month < 10 ? '0' : '') + month;

    const dateString = `${formattedDay}/${formattedMonth}/${year}`;
    return dateString;
}

module.exports = {
    parseStringToDate,
    parseDateToString
}