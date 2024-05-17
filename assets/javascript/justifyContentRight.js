function justifyContentRight(projectCard){
	console.log(projectCard);
	projectCard = projectCard.replace(/<div class="projectCard">/, '<div class="projectCard" id="right">');
	return projectCard;
}