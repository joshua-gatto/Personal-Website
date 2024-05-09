function decoratePreviewCard(previewCard, ...args){
	var [imageUrl, imageAlt, titleText, descriptionText] = args;
	previewCard.replace(/src="[^"]*"/, `src="../resources/${imageUrl}"`).replace(/alt="[^"]*"/, `alt="${imageAlt}"`).replace(/<h2>[^<]*<\/h2>/, `<h2>${titleText}</h2>`).replace(/<p>[^<]*<\/p>/, `<p>${descriptionText}</p2>`);
	return previewCard;
}