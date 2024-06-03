function decoratePortfolioBody(portfolio){
	for(const project of portfolio.projects){
		injectElement(
			"projectCard", 
			"#body", 
			decorateProjectCard, 
			portfolio.portfolioName, 
			project.projectName, 
			project.imageResource, 
			project.imgAlt, 
			project.titleText, 
			project.descriptionText, 
			project.listItems
		);
	}
}