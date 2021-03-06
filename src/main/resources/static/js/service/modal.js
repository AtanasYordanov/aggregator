const modal = (() => {

    function getModalTemplate(headerText, cancellationText, confirmationText) {
        return `
            <div class="modal fade" id="modal" tabindex="-1" role="dialog" 
                aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalCenterTitle">${headerText}</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body px-4"></div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">${cancellationText}</button>
                            <button id="confirm-btn" type="button" class="btn btn-primary">${confirmationText}</button>
                        </div>
                    </div>
                </div>
            </div>
        `
    }

    return {
        getModalTemplate
    };
})();