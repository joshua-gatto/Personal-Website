function decorateSubjectCard(subjectCard, ...args) {
    // Function to recursively remove tags containing null
	args = args.flat(2);
    const removeNullTags = (html) => {
        element.querySelectorAll('*').forEach(content => {
            if (content.includes('null')) {
				content.remove();
            }
        });
    };
    // Recursively process arguments
    const processArgs = (html, args) => {
        args.forEach(arg => {
            if (Array.isArray(arg)) {
                processArgs(html, arg);
            } else {
                element.querySelector(':empty').textContent = arg;
            }
        });
        return html;
    };
    // Process arguments and remove tags containing null
    subjectCard = processArgs(subjectCard, args);
    subjectCard = removeNullTags(subjectCard);
    return subjectCard;
}