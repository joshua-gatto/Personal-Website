function getPortfolioProjects(portfolio){
	const portfolioDirectory = `http://localhost:8000/resources/data/${portfolio}.json`;
	return fetch(portfolioDirectory)
		.then(response => response.json())
		.then(data => {
			return data
		})
		.catch(error => {
			console.log("Error: unknown portfolio", error); 
			return null;
		});
}