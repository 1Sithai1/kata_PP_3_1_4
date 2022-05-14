const usersTab = document.getElementById('home-tab');
const newUserForm = document.getElementById('newUserForm');

const userEditId = document.getElementById('idUserEdit');
const userEditName = document.getElementById('nameEdit');
const userEditLastName = document.getElementById('lastNameEdit');
const userEditAge = document.getElementById('ageEdit');
const userEditEmail = document.getElementById('emailEdit');
const userEditPass = document.getElementById('passwordEdit');

const deleteUserId = document.getElementById('idUserDelete');
const deleteUserName = document.getElementById('nameDelete');
const deleteUserLastName = document.getElementById('lastNameDelete');
const deleteUserAge = document.getElementById('ageDelete');
const deleteUserEmail = document.getElementById('emailDelete');

const selectEdit = document.getElementById('selectEdit');
const selectDelete = document.getElementById('selectDelete');
const editModalBtn = document.getElementById('editModalBtn');
const editModalClose = document.getElementById('editModalClose');
const deleteModalBtn = document.getElementById('deleteModalBtn');
const deleteModalClose = document.getElementById('deleteModalClose');
const crossClose = document.getElementById('crossClose');


let url = 'http://localhost:8080/api/admin';
let urlNewUser = 'http://localhost:8080/api';
let urlRoles = 'http://localhost:8080/api/roles';
let urlUser = 'http://localhost:8080/api/user';

allUsers();

// заполнение таблицы пользователей
function allUsers() {
    const tableBody = document.getElementById('usersTable');
    const navAdminName = document.getElementById('navAdminName');
    const navAdminRole = document.getElementById('navAdminRole');
    let table = '';
    fetch(urlUser)
        .then(res => res.json())
        .then(user => {
            navAdminName.textContent = `${user.email} with roles: `;
            navAdminRole.textContent = `${user.roles.map(role => ' ' + role.name)}`;
        })

    fetch(url)
        .then(res => res.json())
        .then(date => {
            date.forEach(user => {
                table += `
            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.lastName}</td>
                <td>${user.age}</td>
                <td>${user.email}</td>
                <td>
                    <span>${user.roles.map(r => ' ' + r.name)}</span>
                </td>
                <td>
                    <button type="button" class="btn btn-info" data-bs-toggle="modal" id="${user.id}" onclick="editUserDate(${user.id})" href="#modalEdit">
                        Edit
                    </button>
                </td>
                <td>
                    <button type="button" class="btn btn-danger" data-bs-toggle="modal" id="${user.id}" onclick="deleteUserDate(${user.id})" href="#modalDelete">
                        Delete
                    </button>
                </td>
            </tr>`;
            });
            tableBody.innerHTML = table;
        });
}

// добавления ролей в селект формы
const select = document.getElementById('selectNewRole');
let listRoles = '';
fetch(urlRoles)
    .then(res => res.json())
    .then(roles => {
        roles.forEach(role => {
            listRoles += `<option value="${role.name}" data-id = "${role.id}" id="${'role ' + role.id}">${role.name}</option>`
        })
        select.innerHTML = listRoles;
        selectEdit.innerHTML = listRoles;
        selectDelete.innerHTML = listRoles;
    });

//создание нового пользователя
async function newUser() {
    console.log('new User')
    let newUser = {
        name: document.getElementById('name').value,
        lastName: document.getElementById('lastName').value,
        age: document.getElementById('age').value,
        email: document.getElementById('email').value,
        password: document.getElementById('password').value,
        roles: []
    }

    let roles = document.getElementById('selectNewRole').getElementsByTagName('option');
    for (let i = 0; i < roles.length; i++) {
        if (roles[i].selected) {
            await fetch(urlRoles + '/' + roles[i].value)
                .then(res => res.json())
                .then(role => newUser.roles.push(role))
        }
    }
    await fetch(urlNewUser, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(newUser)
    });
}

//редактирование пользователя
async function editUser(id) {
    let user = {
        id: userEditId.value,
        name: userEditName.value,
        lastName: userEditLastName.value,
        age: userEditAge.value,
        email: userEditEmail.value,
        password: userEditPass.value,
        roles: []
    }

    let roles = document.getElementById('selectEdit').getElementsByTagName('option');
    for (let i = 0; i < roles.length; i++) {
        if (roles[i].selected) {
            let role = {
                id: roles[i].getAttribute('data-id'),
                name: roles[i].value
            }
            user.roles.push(role)
        }
    }

    await fetch(url + '/' + id, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(user)
    });
}

//удаление пользователя
async function deleteUser(id) {
    await fetch(url + '/' + id, {
        method: 'DELETE',
    })
        .then(res => res.json());
}

//заполнение полей модального окна редактирования пользователя
function editUserDate(id) {

    fetch(url + '/' + id)
        .then(res => res.json())
        .then(async user => {
            userEditId.value = user.id;
            userEditName.value = user.name;
            userEditLastName.value = user.lastName;
            userEditAge.value = user.age;
            userEditEmail.value = user.email;
            let selectedRolesEdit = user.roles.map(r => r.name);
            selectedRolesEdit.forEach(role => {
                if (role === 'USER') {
                    document.getElementById('role 1').setAttribute('selected', 'selected')
                }
                if (role === 'ADMIN') {
                    document.getElementById('role 2').setAttribute('selected', 'selected')
                }
            })
            editModalBtn.addEventListener('click', () => {
                editUser(user.id);
                usersTab.click();
                location.reload();
            })
            editModalClose.addEventListener('click', () => {
                usersTab.click();
                location.reload();
            })
            crossClose.addEventListener('click', () => {
                usersTab.click();
                location.reload();
            })
        })
}

//заполнение полей модального окна удаления пользователя
function deleteUserDate(id) {

    fetch(url + '/' + id)
        .then(res => res.json())
        .then(async user => {
            deleteUserId.value = user.id;
            deleteUserName.value = user.name;
            deleteUserLastName.value = user.lastName;
            deleteUserAge.value = user.age;
            deleteUserEmail.value = user.email;
            deleteModalBtn.addEventListener('click', () => {
                deleteUser(id);
                usersTab.click();
                location.reload();
            })
            deleteModalClose.addEventListener('click', () => {
                usersTab.click();
                location.reload();
            })
            crossClose.addEventListener('click', () => {
                usersTab.click();
                location.reload();
            })
        })
}

//событие на нажатия субмита формы нового пользователя
newUserForm.addEventListener('submit', e => {
    if (!newUserForm.checkValidity()) {
        e.preventDefault()
        e.stopPropagation()
    } else {
        newUser()
    }
    newUserForm.classList.add('was-validated');
    e.preventDefault();
}, {"once": true});

usersTab.addEventListener('click', () => {
    location.reload();
})
