(function() {

    setTimeout(function() {
        let location = getParameterByName("redirect")
        if(location == undefined) return;
        window.location.href = location
    }, 4000)

})()

function getParameterByName(name, url = window.location.href) {
    name = name.replace(/[\[\]]/g, '\\$&');
    var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, ' '));
} 