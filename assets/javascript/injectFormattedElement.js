function injectElement(htmlResource, preprocessing, targetId){
    window.addEventListener('DOMContentLoaded', function() {
        // TODO: remove/replace http://localhost:8080 when deploying
        loadHTMLBlock(`http://localhost:8000/resources/html/${htmlResource}.html`, injectHTMLBlock, preprocessing, targetId? targetId : null);
    });
}