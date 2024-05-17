/**
 * Asynchronously loads an HTML block from a given URL and processes it with optional preprocessing callbacks before injecting it into the target element.
 *
 * @param {string} url - The URL of the HTML file to load.
 * @param {function} callback - The function to call with the processed HTML content and target tag.
 * @param {function|function[]} preprocessCallback - A single function or an array of functions to preprocess the HTML content.
 * @param {string} targetTag - The CSS selector of the target element where the HTML will be injected.
 * @param {...any} preprocessArgs - Additional arguments to pass to the preprocessing functions.
 */
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
			if(Array.isArray(preprocessCallback)){
				preprocessCallback.forEach((func, index) => {
                    html = func(html, preprocessArgs[index] ? preprocessArgs[index] : null);
                });
			}else{
				html = preprocessCallback(html, preprocessArgs? preprocessArgs : null);
			}
        }
        // verifying event
        if(xhr.readyState == 4 && xhr.status == 200) {
            callback(html, targetTag);
        }
    };
    // send the request
    xhr.send();
}

/**
 * Injects the given HTML content into the specified target element.
 *
 * @param {string} html - The HTML content to inject.
 * @param {string|null} targetTag - The CSS selector of the target element where the HTML will be injected. If null, the content will be injected into the parent of the current script tag.
 */
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