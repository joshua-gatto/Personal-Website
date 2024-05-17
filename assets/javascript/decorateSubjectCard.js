function decorateSubjectCard(subjectCard, ...args) {
    // Function to recursively remove tags containing null
	args = args.flat(2);
    const removeNullTags = (html) => {
        return html.replace(/<(\w+)[^>]*>([\s\S]*?)<\/\1>/g, (match, tag, content) => {
            if (content.includes('null')) {
                return '';
            } else {
                return match;
            }
        });
    };
    // Recursively process arguments
    const processArgs = (html, args) => {
        args.forEach(arg => {
            if (Array.isArray(arg)) {
                html = processArgs(html, arg);
            } else {
                html = html.replace(/<(\w+)><\/\1>/, `<$1>${arg}</$1>`);
            }
        });
        return html;
    };
    // Process arguments and remove tags containing null
    subjectCard = processArgs(subjectCard, args);
    subjectCard = removeNullTags(subjectCard);
    return subjectCard;
}