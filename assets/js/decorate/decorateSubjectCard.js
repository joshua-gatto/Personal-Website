function decorateSubjectCard(subjectCard, ...args) {
    const subject = args.flat()[0];
	const h2 = subjectCard.querySelector("h2");
	h2.textContent = subject.h2;
	console.log(subject.content);
    const contentElement = buildContent(subject.content);
    subjectCard.appendChild(contentElement);
    return subjectCard;
}

function buildContent(elements) {
    try {
        const fragment = document.createDocumentFragment();
        elements.forEach(element => {
            const createdElement = document.createElement(element.tag);
            Object.entries(element).forEach(([key, value]) => {
                if (key !== "tag" && key !== "text") {
                    createdElement.setAttribute(key, value);
                } else if (key === "text") {
                    createdElement.textContent = value;
                }
            });
            fragment.appendChild(createdElement);
        });
        return fragment;
    } catch (error) {
        console.log("Error compiling subjectCard content ", error);
        return null;
    }
}
