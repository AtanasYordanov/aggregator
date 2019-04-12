(function () {
    $(document).ready(function () {

        const itemsPerPage = 20;

        const $tableBody = $('#users-table tbody');
        const $spinner = $('.table-spinner-wrapper');

        let totalUsers;
        let currentPage = 0;

        fetchUsers(currentPage);

        function fetchUsers(page) {
            currentPage = page;
            $tableBody.empty();
            $spinner.show();

            http.get(`/admin/users/page?page=${page}&size=${itemsPerPage}&sort=lastLogin`
                , (data) => {
                    $spinner.hide();
                    renderUsers(data['users']);
                    totalUsers = data['totalItemsCount'];
                    pagination.render(fetchUsers, currentPage, totalUsers, itemsPerPage);
                }
                , () => $spinner.hide());
        }

        function renderUsers(users) {
            users.forEach((user, i) => {
                const $tableRow = $('<tr>');

                const status = user['status'];
                const dateString = CustomUtils.getDateString(user['lastLogin']);

                $tableRow.append($('<td>').text(currentPage * itemsPerPage + i + 1));
                $tableRow.append($('<td>').text(user['email']));
                $tableRow.append($('<td>').text(user['role']).addClass('role-column'));
                $tableRow.append($('<td>').text(dateString));
                $tableRow.append($('<td>').text(status).addClass('status').addClass(status.toLowerCase()));
                $tableRow.append($(`
                        <td class="btn-col">
                            <div class="btn-group btn-group-sm" role="group" aria-label="...">
                                <a class="btn btn-outline-secondary" href="/admin/users/${user['id']}">Details</a>
                            </div>
                        </td>
                    `));

                const $btnGroup = $tableRow.find('.btn-group');

                $btnGroup.append($('<button class="btn btn-outline-primary">Change Role</button>')
                    .on('click', () => displayRolesModal(user['id'], $tableRow)));

                if (status !== 'SUSPENDED') {
                    appendSuspendButton(user, $btnGroup, $tableRow);
                } else {
                    appendActivateButton(user, $btnGroup, $tableRow);
                }

                $tableBody.append($tableRow);
            });
        }

        function appendSuspendButton(user, $btnGroup, $tableRow) {
            const $suspendBtn = $('<button class="btn btn-outline-danger">Suspend</button>');
            $btnGroup.append($suspendBtn
                .on('click', () => suspendUser(user, $btnGroup, $tableRow, $suspendBtn))
            );
        }

        function appendActivateButton(user, $btnGroup, $tableRow) {
            const $activateBtn = $('<button class="btn btn-outline-success">Activate</button>');
            $btnGroup.append($activateBtn
                .on('click', () => activateUser(user, $btnGroup, $tableRow, $activateBtn))
            );
        }

        function suspendUser(user, $btnGroup, $tableRow, $suspendBtn) {
            http.put(`/admin/suspend/${user['id']}`, {},
                () => {
                    const $statusField = $tableRow.find('.status');
                    $statusField.text('SUSPENDED');
                    $statusField.removeClass(user['status'].toLowerCase());
                    $statusField.addClass('suspended');
                    $suspendBtn.remove();
                    user['status'] = 'SUSPENDED';
                    appendActivateButton(user, $btnGroup, $tableRow)
                });
        }

        function activateUser(user, $btnGroup, $tableRow, $activateBtn) {
            http.put(`/admin/activate/${user['id']}`, {},
                () => {
                    const $statusField = $tableRow.find('.status');
                    $statusField.text('ACTIVE');
                    $statusField.removeClass(user['status'].toLowerCase());
                    $statusField.addClass('active');
                    $activateBtn.remove();
                    user['status'] = 'ACTIVE';
                    appendSuspendButton(user, $btnGroup, $tableRow)
                });
        }


        function displayRolesModal(userId, $tableRow) {
            $('#modal').remove();

            const $modal = $(modal.getModalTemplate('Change user role', 'CANCEL', 'UPDATE'));
            const $selectImport = buildRoleSelectBox();

            $modal.find('#confirm-btn').on('click', () => changeUserRole(userId, $modal, $tableRow));
            $modal.find('.modal-body').append($selectImport);

            $('body').append($modal);
            $modal.modal();
        }

        function changeUserRole(userId, $modal, $tableRow) {
            const selectedRole = $('#role-select').val();

            const data = {
                userId: userId,
                roleName: selectedRole
            };

            http.post('/admin/roles/update', data
                , () => {
                    $tableRow.find('.role-column').text(selectedRole);
                    $modal.modal('hide');
                    notification.success('Successfully changed user role.');
                }, () => $modal.modal('hide'));
        }

        function buildRoleSelectBox() {
            const $selectImport = $(`
                    <div class="form-row">
                        <div class="form-group col-lg-6">
                            <label for="role-select"> </label>
                            <select id="role-select" class="form-control">
                                <option disabled selected>Select User Role</option>
                            </select>
                        </div>
                    </div>
                `);

            http.get('/admin/roles'
                , (roles) => {
                    const $options = $selectImport.find('#role-select');
                    roles.forEach(role => $options.append($(`<option>`).val(role).text(role)));
                });

            return $selectImport;
        }
    });
})();