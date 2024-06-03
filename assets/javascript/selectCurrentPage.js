function selectCurrentPage(navbar){
    const currentPagePath = window.location.pathname;
    const links = navbar.querySelectorAll('a');
    links.forEach(link => {
        const href = link.getAttribute('href');
        if (href === currentPagePath) {
            const div = link.querySelector('div');
            if (div) {
                div.setAttribute('id', 'currentPage');
            }
        }
    });
    return navbar;
}