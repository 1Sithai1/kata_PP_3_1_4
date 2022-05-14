let urlUser = 'http://localhost:8080/api/user';
let tableUser = '';

userPage();

function userPage() {
    const userTableBody = document.getElementById('userTableBody');
    const navUserName = document.getElementById('navUserName');
    const navUserRole = document.getElementById('navUserRole');
    fetch(urlUser)
        .then(res => res.json())
        .then(user => {
            navUserName.textContent = `${user.email} with roles: `;
            navUserRole.textContent = `${user.roles.map(role => ' ' + role.name)}`;
            tableUser += `
            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.lastName}</td>
                <td>${user.age}</td>
                <td>${user.email}</td>
                <td>
                    <span>${user.roles.map(r => ' ' + r.name)}</span>
                </td>
            </tr>`;
            userTableBody.innerHTML = tableUser;
        });
}