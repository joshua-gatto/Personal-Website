function loadHTMLBlock(url, callback, targetId) {
    // Async. fetches HTML file
    var xhr = new XMLHttpRequest();
    // initialize request
    xhr.open('GET', url, true);
    // event listener
    xhr.onreadystatechange = function() {
        // verifying event
        if(xhr.readyState == 4 && xhr.status == 200) {
            callback(xhr.responseText, targetId);
        }
    };
    // send the request
    xhr.send();
}

function insertHTMLBlock(html, targetId) {
    // if target is specified
    if(targetId == null) {
        // get script tags
        var scriptTag = document.currentScript || (function() {
            var scripts = document.getElementsByTagName('script');
            return scripts[scripts.length - 1];
        })();
        var target = scriptTag.parentElement;
    } else {
        // get target
        var target = document.getElementById(targetId);
    }
    target.innerHTML = html;
}