function decorateProjectBody(project){
	project.subjects.forEach(subject => {
		injectElement("subjectCard", "div#body", decorateSubjectCard, subject);
	});
}