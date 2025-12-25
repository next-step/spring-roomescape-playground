let isEditing = false;
const RESERVATION_API_ENDPOINT = '/reservations';

document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('add-reservation').addEventListener('click', addEditableRow);
    fetchReservations();
});

function fetchReservations() {
    requestRead()
        .then(renderReservations)
        .catch(error => console.error('Error fetching reservations:', error));
}

function renderReservations(data) {
    const tableBody = document.getElementById('reservation-table-body');
    tableBody.innerHTML = '';

    data.forEach((reservation, index) => {
        const row = tableBody.insertRow();
        insertReservationRow(row, reservation, index + 1);
    });
}

function insertReservationRow(row, reservation, displayIndex) {
    row.insertCell(0).textContent = displayIndex;
    row.insertCell(1).textContent = reservation.name;
    row.insertCell(2).textContent = reservation.date;
    row.insertCell(3).textContent = reservation.time;

    const actionCell = row.insertCell(4);
    const deleteButton = createActionButton('삭제', 'btn-danger', deleteRow);
    deleteButton.setAttribute('data-reservation-id', reservation.id);
    actionCell.appendChild(deleteButton);
}

function createActionButton(label, className, eventListener) {
    const button = document.createElement('button');
    button.textContent = label;
    button.classList.add('btn', className, 'mr-2');
    button.addEventListener('click', eventListener);
    return button;
}

function addEditableRow() {

    if (isEditing) return;  // 이미 편집 중인 경우 추가하지 않음

    const tableBody = document.getElementById('reservation-table-body');
    const row = tableBody.insertRow();
    isEditing = true;

    createEditableFieldsFor(row);
    addSaveAndCancelButtonsToRow(row);
}

function createEditableFieldsFor(row) {
    const fields = ['', createInput('text'), createInput('date'), createInput('time')];
    fields.forEach((field, index) => {
        const cell = row.insertCell(index);
        if (typeof field === 'string') {
            cell.textContent = field;
        } else {
            cell.appendChild(field);
        }
    });
}

function addSaveAndCancelButtonsToRow(row) {
    const actionCell = row.insertCell(4);
    actionCell.appendChild(createActionButton('확인', 'btn-primary', saveRow));
    actionCell.appendChild(createActionButton('취소', 'btn-secondary', () => {
        row.remove();
        isEditing = false;
    }));
}

function createInput(type) {
    const input = document.createElement('input');
    input.type = type;
    input.className = 'form-control';
    return input;
}

function saveRow(event) {
    const row = event.target.parentNode.parentNode;
    const inputs = row.querySelectorAll('input');

    const reservation = {
        name: inputs[0].value, date: inputs[1].value, time: inputs[2].value
    };

    requestCreate(reservation)
        .then(() => {
            fetchReservations();
            isEditing = false;
        })
        .catch(error => console.error('Error:', error));
}

function deleteRow(event) {
    const button = event.target;
    const reservationId = button.getAttribute('data-reservation-id');

    requestDelete(reservationId)
        .then(() => {
            fetchReservations();
        })
        .catch(error => console.error('Error:', error));
}

function requestCreate(reservation) {
    const requestOptions = {
        method: 'POST', headers: {'Content-Type': 'application/json'}, body: JSON.stringify(reservation)
    };

    return fetch(RESERVATION_API_ENDPOINT, requestOptions)
        .then(response => {
            if (response.status === 201) return response.json();
            throw new Error('Create failed');
        });
}

function requestRead() {
    return fetch(RESERVATION_API_ENDPOINT)
        .then(response => {
            if (response.status === 200) return response.json();
            throw new Error('Read failed');
        });
}

function requestDelete(id) {
    const requestOptions = {
        method: 'DELETE',
    };

    return fetch(`${RESERVATION_API_ENDPOINT}/${id}`, requestOptions)
        .then(response => {
            if (response.status !== 204) throw new Error('Delete failed');
        });
}
