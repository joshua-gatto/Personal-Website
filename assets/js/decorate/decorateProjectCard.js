function decorateProjectCard(projectCard, ...args){
	const [portfolio, project, imageResource, imageAlt, titleText, descriptionText, listItems] = args.flat();
	const projectLink = projectCard;
	const data = {portfolio: portfolio, project: project};
	const encodedData = encodeURIComponent(JSON.stringify(data));
	const img = projectCard.querySelector('img');
	const title = projectCard.querySelector('h2');
	const description = projectCard.querySelector('p');
	const list = projectCard.querySelector('ul');
	projectLink.href = `http://localhost:8000/html/project.html?data=${encodedData}`;
	img.src = `/resources/images/${project}/${imageResource}`;
	img.alt = imageAlt;
	title.textContent = titleText;
	description.textContent = descriptionText;
	list.innerHTML = listItems.map(item => `<li>${item}</li>`).join('');
	return projectCard;
}