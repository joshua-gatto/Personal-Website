function loadHTMLBlock(url, callback, preprocessCallback, targetTag, ...preprocessArgs) {
    // Async. fetches HTML file
    var xhr = new XMLHttpRequest();
    // initialize request
    xhr.open('GET', url, true);
    // event listener
    xhr.onreadystatechange = function() {
        //preprocessing html block
        var html = xhr.responseText;
        if(preprocessCallback){
            html = preprocessCallback(html, preprocessArgs? preprocessArgs : null);
        }
        // verifying event
        if(xhr.readyState == 4 && xhr.status == 200) {
            callback(html, targetTag);
        }
    };
    // send the request
    xhr.send();
}

function injectHTMLBlock(html, targetTag) {
    // if target is specified
    if(targetTag == null) {
        // finds the last script tag
        var scriptTag = document.currentScript || (function() {
            var scripts = document.querySelectorAll('script');
            return scripts[scripts.length - 1];
        })();
        var target = scriptTag.parentElement;
    } else {
        // get target
        var target = document.querySelector(`${targetTag}`);
    }
    target.innerHTML += html;
}