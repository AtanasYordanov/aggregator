(() => {

    const $firstNameInput = $('#first-name');
    const $lastNameInput = $('#last-name');
    const $emailInput = $('#email');
    const $passwordInput = $('#password');
    const $confirmPasswordInput = $('#confirm-password');

    const emailRegex = /^[A-Za-z0-9+_.-]+@[A-Za-z0-9+_.-]+$/;

    $('#register-btn').on('click', (e) => validateFields(e));


    function validateFields(e) {

        const firstName = $firstNameInput.val();
        const lastName = $lastNameInput.val();
        const email = $emailInput.val();
        const password = $passwordInput.val();
        const confirmPassword = $confirmPasswordInput.val();

        let error = false;

        if (!firstName || firstName.length < 2) {
            notification.error('First name must be at least 2 symbols long!');
            error = true;
        }

        if (!lastName || lastName.length < 2) {
            notification.error('Last name must be at least 2 symbols long!');
            error = true;
        }

        if (!email || email.length < 6) {
            notification.error('Email must be at least 6 symbols long!');
            error = true;
        } else if (!emailRegex.test(email)) {
            notification.error('Invalid email!');
            error = true;
        }

        if (!password || password.length < 3) {
            notification.error('Password must be at least 3 symbols long!');
            error = true;
        }

        if (password !== confirmPassword) {
            notification.error('Passwords do not match!');
            error = true;
        }

        if (error) {
            e.preventDefault();
        }
    }
})();

