function decoratePortfolioCard(portfolioCard, ...args){
	var [imgSrc, h2, h4] = args.flat(2);
	portfolioCard = portfolioCard.replace(/src="[^"]*"/, `src="/resources/images/${imgSrc}_directory_image.png"`).replace(/<h2>[^<]*<\/h2>/, `<h2>${h2}</h2>`).replace(/<h4>[^<]*<\/h4>/, `<h4>${h4}</h4>`);
	return portfolioCard;
}