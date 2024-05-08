function loadHTMLBlock(url, callback, preprocessCallback, targetId) {
    // Async. fetches HTML file
    var xhr = new XMLHttpRequest();
    // initialize request
    xhr.open('GET', url, true);
    // event listener
    xhr.onreadystatechange = function() {
        //preprocessing html block
        var html = xhr.responseText;
        if(preprocessCallback){
            html = preprocessCallback(html);
        }
        // verifying event
        if(xhr.readyState == 4 && xhr.status == 200) {
            callback(html, targetId);
        }
    };
    // send the request
    xhr.send();
}

function injectHTMLBlock(html, targetId) {
    // if target is specified
    if(targetId == null) {
        // get script tags
        var scriptTag = document.currentScript || (function() {
            var scripts = document.querySelectorAll('script');
            return scripts[scripts.length - 1];
        })();
        var target = scriptTag.parentElement;
    } else {
        // get target
        var target = document.querySelector('#targetId');
    }
    target.innerHTML = html;
}