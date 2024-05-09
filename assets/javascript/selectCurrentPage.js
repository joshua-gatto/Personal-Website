function selectCurrentPage(navbar){
    const currentPagePath = window.location.pathname;
    const linkRegex = /<a\s+href="([^"]+)"[^>]*>\s*<div>\s*<p>([^<]+)<\/p>\s*<\/div>\s*<\/a>/g;
    let match;
    while((match = linkRegex.exec(navbar)) !== null) {
        const href = match[1];
        const linkText = match[2];
        if(href === currentPagePath) {
            navbar= navbar.replace(match[0], match[0]. replace('<div>', '<div id="currentPage">'));
        }
    }
    return navbar;
}