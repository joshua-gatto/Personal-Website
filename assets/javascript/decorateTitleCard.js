function decorateTitleCard(titleCard, ...args){
	var [title, subtitle] = args.flat(2);
	titleCard = titleCard.replace(/<h1 id="mainTitle">[^<]*<\/h1>/, `<h1 id="mainTitle">${title}</h1>`).replace(/<h3 id="subTitle">[^<]*<\/h3>/, `<h3 id="subTitle">${subtitle}</h3>`);
	return titleCard;
}