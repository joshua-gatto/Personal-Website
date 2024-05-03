function loadHTMLBlock(url, callback) {
    // Async. fetches HTML file
    var xhr = new XMLHttpRequest();
    // initialize request
    xhr.open('GET', url, true);
    // event listener
    xhr.onreadystatechange = function() {
        // verifying event
        if(xhr.readyState == 4 && xhr.status == 200) {
            callback(xhr.responseText);
        }
    };
    // send the request
    xhr.send();
}

function insertHTMLBlock(html) {
    // get parent of script tag
    var scriptTag = document.currentScript || (function() {
        var scripts = document.getElementsByTagName('script');
        return scripts[scripts.length - 1];
    })();
    var parent = scriptTag.parentElement;
    parent.innerHTML = html;
}