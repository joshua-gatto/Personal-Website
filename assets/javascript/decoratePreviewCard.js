function decoratePreviewCard(previewCard, ...args){
	var [imageResource, imageAlt, titleText, descriptionText] = args.flat(2);
	previewCard = previewCard.replace(/src="[^"]*"/, `src="../resources/${imageResource}"`).replace(/alt="[^"]*"/, `alt="${imageAlt}"`).replace(/<h2>[^<]*<\/h2>/, `<h2>${titleText}</h2>`).replace(/<p>[^<]*<\/p>/, `<p>${descriptionText}</p>`);
	return previewCard;
}