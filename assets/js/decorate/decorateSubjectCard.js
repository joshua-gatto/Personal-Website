function decorateSubjectCard(subjectCard, ...args) {
    const subject = args.flat()[0];
    const contentElement = buildContent(subject);
    subjectCard.appendChild(contentElement);
    return subjectCard;
}

function buildContent(element) {
    try {
        const createdElement = document.createElement(element.tag);
        Object.entries(element).forEach(([key, value]) => {
            if (key !== "tag" && key !== "text") {
                createdElement.setAttribute(key, value);
            } else if (key === "text") {
                createdElement.textContent = value;
            }
        });
        return createdElement;
    } catch (error) {
        console.log("Error compiling subjectCard content ", error);
        return null;
    }
}