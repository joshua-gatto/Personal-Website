//file needs to be renamed. possibly inject local element
function injectElement(htmlResource, preprocessing, targetTag, ...preprocessArgs){
    window.addEventListener('DOMContentLoaded', function() {
        // TODO: remove/replace http://localhost:8080 when deploying
        loadHTMLBlock(`http://localhost:8000/resources/html/${htmlResource}.html`, injectHTMLBlock, preprocessing, targetTag? targetTag : null, preprocessArgs? preprocessArgs : null);
    });
}