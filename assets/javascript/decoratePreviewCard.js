function decoratePreviewCard(previewCard, ...args){
	var [imageResource, imageAlt, titleText, descriptionText, buttonText, buttonLink] = args.flat(2);
	previewCard = previewCard.replace(/src="[^"]*"/, `src="../resources/${imageResource}"`).replace(/alt="[^"]*"/, `alt="${imageAlt}"`).replace(/<h2>[^<]*<\/h2>/, `<h2>${titleText}</h2>`).replace(/<p>[^<]*<\/p>/, `<p>${descriptionText}</p>`).replace(/<input type="button"[^>]*>/, `<input type="button" value="${buttonText}" onclick="location.href='/html/${buttonLink}.html'">`);
	return previewCard;
}