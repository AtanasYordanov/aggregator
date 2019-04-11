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
                , () => notification.error("Failed to load the employees catalog."));
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

                $tableRow.find('.btn-group')
                    .append($('<button class="btn btn-outline-info">Change Role</button>')
                        .on('click', () => displayRolesModal(user['id'], $tableRow)));

                $tableRow.find('.btn-group')
                    .append($('<button class="btn btn-outline-danger">Suspend</button>'));

                $tableBody.append($tableRow);
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
                }, () => {
                    $modal.modal('hide');
                    notification.error('Failed to change user role.');
                });
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
                }
                , () => notification.error("Failed to load roles."));
            return $selectImport;
        }
    });
})();