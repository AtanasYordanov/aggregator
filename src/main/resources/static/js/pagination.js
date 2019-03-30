let pagination = (() => {

    const $pagination = $('.pagination');
    const $matchesBox = $('.matches-box');

    let totalPages;
    let currentPage;

    function render(fetchFunction, page, totalMatches, itemsPerPage) {
        currentPage = page;
        totalPages = totalMatches % itemsPerPage === 0
            ? parseInt(totalMatches / itemsPerPage)
            : parseInt(totalMatches / itemsPerPage) + 1;

        $pagination.empty();

        renderFirstPageButton(fetchFunction);
        renderPreviousPageButton(fetchFunction);

        let from = Math.max(0, currentPage - 3);
        let to = Math.min(totalPages - 1, currentPage + 3);
        to = to - from < 6 ? Math.min(from + 6, totalPages - 1) : to;
        from = to - from < 6 ? Math.max(to - 6, 0) : from;

        for (let i = from; i <= to; i++) {
            renderPageLink(i, fetchFunction);
        }

        renderNextPageButton(fetchFunction);
        renderLastPageButton(fetchFunction);

        $matchesBox.text('Total matches: ' + totalMatches);
    }

    function renderFirstPageButton(fetchFunction) {
        const $firstPageBtn = $(`<li class="page-item">`
            + `<a href="#" class="page-link">First</a>`
            + `</li>`);
        $firstPageBtn.on('click', function (e) {
            e.preventDefault();
            fetchFunction(0)
        });
        $pagination.append($firstPageBtn);
    }

    function renderLastPageButton(fetchFunction) {
        const $lastPageBtn = $(`<li class="page-item">`
            + `<a href="#" class="page-link">Last</a>`
            + `</li>`);
        $lastPageBtn.on('click', function (e) {
            e.preventDefault();
            fetchFunction(totalPages - 1)
        });
        $pagination.append($lastPageBtn);
    }

    function renderNextPageButton(fetchFunction) {
        const $nextBtn = $(`<li class="page-item ${currentPage + 1 === totalPages ? 'disabled' : ''}">`
            + `<a href="#" class="page-link">&raquo;</a>`
            + `</li>`);

        const disableNextBtn = currentPage === totalPages - 1;
        if (!disableNextBtn) {
            $nextBtn.on('click', function (e) {
                e.preventDefault();
                fetchFunction(currentPage + 1)
            });
        }
        $pagination.append($nextBtn);
    }

    function renderPreviousPageButton(fetchFunction) {
        const $prevBtn = $(`<li class="page-item ${currentPage === 0 ? 'disabled' : ''}">`
            + `<a href="#" class="page-link">&laquo;</a>`
            + `</li>`);

        const disablePrevBtn = currentPage === 0;
        if (!disablePrevBtn) {
            $prevBtn.on('click', function (e) {
                e.preventDefault();
                fetchFunction(currentPage - 1)
            });
        }
        $pagination.append($prevBtn);
    }

    function renderPageLink(i, fetchFunction) {
        const $pageLink = $(`<li class="page-item ${currentPage === i ? 'active' : ''}">`
            + `<a href="#" class="page-link">${i + 1}</a>`
            + `</li>`);

        $pageLink.on('click', function (e) {
            e.preventDefault();
            fetchFunction(i)
        });

        $pagination.append($pageLink);
    }

    return {
        render
    };
})();