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
            console.log(users);
            users.forEach((user, i) => {
                const $tableRow = $('<tr>');

                const status = user['status'];
                const dateString = CustomUtils.getDateString(user['lastLogin']);

                $tableRow.append($('<td>').text(currentPage * itemsPerPage + i + 1));
                $tableRow.append($('<td>').text(user['email']));
                $tableRow.append($('<td>').text(user['role']));
                $tableRow.append($('<td>').text(dateString));
                $tableRow.append($('<td>').text(status).addClass('status').addClass(status.toLowerCase()));
                $tableRow.append($(`
                        <td class="btn-col">
                            <div class="btn-group btn-group-sm" role="group" aria-label="...">
                                <a href="/admin/users/${user['id']}" class="btn btn-outline-secondary">Details</a>
                                <a href="/admin/users/" class="btn btn-outline-info">Change Role</a>
                                <a href="/admin/users/" class="btn btn-outline-danger">Suspend</a>
                            </div>
                        </td>
                    `));

                $tableBody.append($tableRow);
            });
        }
    });
})();