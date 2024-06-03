function decorateTitleCard(titleCard, ...args){
	const [title, subtitle] = args.flat(2);
	titleCard.querySelector('#mainTitle').textContent = title;
    titleCard.querySelector('#subTitle').textContent = subtitle;
	return titleCard;
}