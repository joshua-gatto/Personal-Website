//file needs to be renamed. possibly inject local element
async function injectElement(htmlResource, targetTag, postProcessing, ...postProcessArgs){
    window.addEventListener('DOMContentLoaded', async function() {
		const resourceURL = `http://localhost:8000/resources/html/${htmlResource}.html`;
		try{
			//fetch resources
			const response = await fetch(resourceURL);
			const htmlContent = await response.text();
			//instantite resource
			var element = new DOMParser().parseFromString(htmlContent, 'text/html');
			//get target
			const targetElement = document.querySelector(targetTag);
			//decorate
			if(postProcessing){
				if(Array.isArray(postProcessing)){
					postProcessing.forEach((func, index) => {
						element = func(element, postProcessArgs[index] ? postProcessArgs[index] : null);
					});
				}else{
					element = postProcessing(element, postProcessArgs? postProcessArgs : null);
				}
			}
			targetElement.innerHTML = element.documentElement.innerHTML;
		} catch(error){
			console.log(error);
		}
        // TODO: remove/replace http://localhost:8080 when deploying
        // loadHTMLBlock(`http://localhost:8000/resources/html/${htmlResource}.html`, injectHTMLBlock, preprocessing, targetTag? targetTag : null, preprocessArgs? preprocessArgs : null);
    });
}