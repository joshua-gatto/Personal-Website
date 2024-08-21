function decorateSideBar(sideBar, subjects){
	const list = sideBar.querySelector("ul");
	subjects[0].forEach(subject => {
		const listElement = document.createElement("li");
		const subjectLink = document.createElement("a");
		subjectLink.href = `#${subject.h2.replace(/\s+/g, '-').toLowerCase()}`;
		subjectLink.textContent = subject.h2;
		listElement.appendChild(subjectLink);
		list.appendChild(listElement);
	});
	return sideBar;
}