function decorateProjectCard(projectCard, ...args){
	var [linkHref, imageResource, imageAlt, titleText, descriptionText, listItems] = args.flat(2);
	projectCard = projectCard.replace(/href="[^"]*"/, `href="http://localhost:8000/html/${linkHref}.html"`).replace(/src="[^"]*"/, `src="../resources/${imageResource}"`).replace(/alt="[^"]*"/, `alt="${imageAlt}"`).replace(/<h2>[^<]*<\/h2>/, `<h2>${titleText}</h2>`).replace(/<p>[^<]*<\/p>/, `<p>${descriptionText}</p>`);
	if(listItems && listItems.length > 0) {
        const listHtml = listItems.map(item => `<li>${item}</li>`).join('');
        projectCard = projectCard.replace(/<ul>.*?<\/ul>/s, `<ul>${listHtml}</ul>`);
    } else {
        projectCard = projectCard.replace(/<ul>.*?<\/ul>/s, '');
    }
	return projectCard;
}