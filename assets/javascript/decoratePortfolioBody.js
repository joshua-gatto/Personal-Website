function decoratePortfolioBody(portfolio){
	for(const project of portfolio.projects){
		injectElement(
			"projectCard", 
			"#body", 
			decorateProjectCard, 
			project.projectName, 
			project.imageResource, 
			project.imgAlt, 
			project.titleText, 
			project.descriptionText, 
			project.listItems
		);
	}
}