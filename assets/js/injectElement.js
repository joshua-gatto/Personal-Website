//file needs to be renamed. possibly inject local element
async function injectElement(htmlResource, targetTag, postProcessing, ...postProcessArgs){
	const resourceURL = `http://localhost:8000/resources/html/${htmlResource}.html`;
	//get target
	const targetElement = document.querySelector(targetTag);
	try{
		//fetch resources
		const response = await fetch(resourceURL);
		const htmlContent = await response.text();
		//instantite resource
		const parser = new DOMParser();
		const doc = parser.parseFromString(htmlContent, 'text/html');
		var element = doc.body.firstElementChild;
		//decorate
		if(postProcessing !== null){
			if(Array.isArray(postProcessing)){
				postProcessing.forEach((func, index) => {
					element = func(element, postProcessArgs[index] ? postProcessArgs[index] : null);
				});
			}else{
				element = postProcessing(element, postProcessArgs? postProcessArgs : null);
			}
		}
		targetElement.appendChild(element);
	} catch(error){
		console.log("Error injecting element " + htmlResource, error);
	}
}