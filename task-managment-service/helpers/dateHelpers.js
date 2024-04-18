

function parseStringToDate(string) {
    const parts = string.split('/');

    const date = new Date(Date.UTC(parseInt(parts[2]), parseInt(parts[1]) - 1, parseInt(parts[0])));
    return date;
}

function parseDateToString(dateString) {
    const dateObject = new Date(dateString);

    const day = dateObject.getUTCDate();
    const month = dateObject.getUTCMonth() + 1;
    const year = dateObject.getUTCFullYear();

    const formattedDate = `${day < 10 ? '0' : ''}${day}/${month < 10 ? '0' : ''}${month}/${year}`;

    return formattedDate
}

module.exports = {
    parseStringToDate,
    parseDateToString
}