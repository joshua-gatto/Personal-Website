function decoratePreviewCard(previewCard, ...args){
	const [imageResource, imageAlt, titleText, descriptionText, buttonText, buttonLink] = args.flat(2);
	previewCard.querySelector('img').src = `/resources/images/${imageResource}_directory_image.png`;
    previewCard.querySelector('img').alt = imageAlt;
	previewCard.querySelector('h2').textContent = titleText;
    previewCard.querySelector('p').textContent = descriptionText;
	buttonElement = previewCard.querySelector('input[type="button"]');
	buttonElement.value = buttonText;
	buttonElement.onclick = function(){
		location.href = `/html/${buttonLink}.html`;
	};
	return previewCard;
}