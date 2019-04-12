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

        const screenWidth = $(document).width();
        let additionLinksShown = screenWidth > 800 ? 6
            : screenWidth > 550 ? 4 : 2;

        let from = Math.max(0, currentPage - additionLinksShown / 2);
        let to = Math.min(totalPages - 1, currentPage + additionLinksShown / 2);
        to = to - from < additionLinksShown ? Math.min(from + additionLinksShown, totalPages - 1) : to;
        from = to - from < additionLinksShown ? Math.max(to - additionLinksShown, 0) : from;

        for (let i = from; i <= to; i++) {
            renderPageLink(i, fetchFunction);
        }

        renderNextPageButton(fetchFunction);
        renderLastPageButton(fetchFunction);

        $matchesBox.text('Total items: ' + totalMatches);
    }

    function renderFirstPageButton(fetchFunction) {
        const disableFirstPageBtn = currentPage === 0;

        const $firstPageBtn = $(`<li class="page-item ${disableFirstPageBtn ? 'disabled' : ''}">`
            + `<a href="#" class="page-link">First</a>`
            + `</li>`);

        if (!disableFirstPageBtn) {
            $firstPageBtn.on('click', function (e) {
                e.preventDefault();
                fetchFunction(0)
            });
        }
        $pagination.append($firstPageBtn);
    }

    function renderPreviousPageButton(fetchFunction) {
        const disablePrevBtn = currentPage === 0;

        const $prevBtn = $(`<li class="page-item ${disablePrevBtn ? 'disabled' : ''}">`
            + `<a href="#" class="page-link">&laquo;</a>`
            + `</li>`);

        if (!disablePrevBtn) {
            $prevBtn.on('click', function (e) {
                e.preventDefault();
                fetchFunction(currentPage - 1)
            });
        }
        $pagination.append($prevBtn);
    }

    function renderNextPageButton(fetchFunction) {
        const disableNextBtn = currentPage === totalPages - 1;

        const $nextBtn = $(`<li class="page-item ${disableNextBtn ? 'disabled' : ''}">`
            + `<a href="#" class="page-link">&raquo;</a>`
            + `</li>`);

        if (!disableNextBtn) {
            $nextBtn.on('click', function (e) {
                e.preventDefault();
                fetchFunction(currentPage + 1)
            });
        }
        $pagination.append($nextBtn);
    }

    function renderLastPageButton(fetchFunction) {
        const disableLastPageBtn = currentPage === totalPages - 1;

        const $lastPageBtn = $(`<li class="page-item ${disableLastPageBtn ? 'disabled' : ''}">`
            + `<a href="#" class="page-link">Last</a>`
            + `</li>`);

        if (!disableLastPageBtn) {
            $lastPageBtn.on('click', function (e) {
                e.preventDefault();
                fetchFunction(totalPages - 1)
            });
        }
        $pagination.append($lastPageBtn);
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