(() => {

    const $oldPasswordInput = $('#old-password');
    const $newPasswordInput = $('#new-password');
    const $confirmNewPasswordInput = $('#confirm-new-password');

    $('#update-btn').on('click', (e) => validateFields(e));


    function validateFields(e) {

        const oldPassword = $oldPasswordInput.val();
        const newPassword = $newPasswordInput.val();
        const confirmNewPassword = $confirmNewPasswordInput.val();

        let error = false;

        if (!oldPassword || oldPassword.length < 3) {
            notification.error('Password must be at least 3 symbols long!');
            error = true;
        }

        if (!newPassword || oldPassword.length < 3) {
            notification.error('Password must be at least 3 symbols long!');
            error = true;
        }

        if (newPassword !== confirmNewPassword) {
            notification.error('Passwords do not match!');
            error = true;
        }

        if (error) {
            e.preventDefault();
        }
    }
})();

