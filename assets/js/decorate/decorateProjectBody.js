function decorateProjectBody(project){
	project.subjects.forEach(subject => {
		injectElement("subjectCard", "div#mainContent", decorateSubjectCard, subject);
	});
}