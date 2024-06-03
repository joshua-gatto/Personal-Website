function decoratePortfolioCard(portfolioCard, ...args){
	const [imgSrc, h2, p] = args.flat(2);
	const portfolio = imgSrc;
	const img = portfolioCard.querySelector('img')
	const title = portfolioCard.querySelector('h2');
	const subtitle = portfolioCard.querySelector('p');
	const button = portfolioCard.querySelector('input[type="button"]');
	img.src = `/resources/images/${imgSrc}_directory_image.png`;
    title.textContent = h2;
    subtitle.textContent = p;
	button.addEventListener("click", function(){
		redirectUser(portfolio);
	});
	return portfolioCard;
}

function redirectUser(portfolio){
	const encodedPortfolio = encodeURIComponent(portfolio);
	const url = `/html/portfolios/portfolio.html?portfolio=${encodedPortfolio}`;
	window.location.href = url;
}