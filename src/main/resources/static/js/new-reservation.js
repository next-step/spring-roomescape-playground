let isEditing = false;
const RESERVATION_API_ENDPOINT = '/reservations';
const TIME_API_ENDPOINT = '/times';

document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('add-reservation').addEventListener('click', addEditableRow);
    fetchReservations();
});

function fetchTimesByDate(date, timeSelectElement) {
    fetch(`${TIME_API_ENDPOINT}?date=${date}`)
        .then(response => {
            if (response.status === 200) return response.json();
            throw new Error('Read failed');
        })
        .then(data => {
            timeSelectElement.innerHTML = '<option value="">시간 선택</option>';

            data.forEach(time => {
                const option = document.createElement('option');
                option.value = time;
                option.textContent = time;
                timeSelectElement.appendChild(option);
            });
        })
        .catch(error => console.error('Error fetching time:', error));
}

function fetchReservations() {
    requestRead()
        .then(renderReservations)
        .catch(error => console.error('Error fetching reservations:', error));
}

function renderReservations(data) {
    const tableBody = document.getElementById('reservation-table-body');
    tableBody.innerHTML = '';

    data.forEach(reservation => {
        const row = tableBody.insertRow();
        insertReservationRow(row, reservation);
    });
}

function insertReservationRow(row, reservation) {
    ['id', 'name', 'date'].forEach((field, index) => {
        row.insertCell(index).textContent = reservation[field];
    });

    row.insertCell(3).textContent = reservation.time;

    const actionCell = row.insertCell(4);
    actionCell.appendChild(createActionButton('삭제', 'btn-danger', deleteRow));
}

function createActionButton(label, className, eventListener) {
    const button = document.createElement('button');
    button.textContent = label;
    button.classList.add('btn', className, 'mr-2');
    button.addEventListener('click', eventListener);
    return button;
}

function addEditableRow() {
    if (isEditing) return;

    const tableBody = document.getElementById('reservation-table-body');
    const row = tableBody.insertRow();
    isEditing = true;

    createEditableFieldsFor(row);
    addSaveAndCancelButtonsToRow(row);
}

// 2. [수정] 날짜 선택창과 빈 시간 드롭다운을 만들고 이벤트를 바인딩하는 핵심 로직
function createEditableFieldsFor(row) {
    const nameInput = createInput('text');
    const dateInput = createInput('date');

    // 기존 HTML에서 복사해오는 대신, 빈 select 박스를 동적으로 생성합니다.
    const timeDropdown = document.createElement('select');
    timeDropdown.className = 'form-control';
    timeDropdown.innerHTML = '<option value="">날짜를 먼저 선택하세요</option>';

    // 🔥 사용자가 날짜를 변경하면 서버에 해당 날짜의 가용 시간을 물어봅니다.
    dateInput.addEventListener('change', (event) => {
        const selectedDate = event.target.value;
        if (selectedDate) {
            fetchTimesByDate(selectedDate, timeDropdown);
        }
    });

    const fields = ['', nameInput, dateInput, timeDropdown];

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
    const nameInput = row.querySelector('input[type="text"]');
    const dateInput = row.querySelector('input[type="date"]');
    const timeSelect = row.querySelector('select');

    const reservation = {
        name: nameInput.value,
        date: dateInput.value,
        time: timeSelect.value // 선택된 "08:00" 같은 문자열이 날아감
    };

    requestCreate(reservation)
        .then(data => updateRowWithReservationData(row, data))
        .catch(error => console.error('Error:', error));

    isEditing = false;
}

function updateRowWithReservationData(row, data) {
    const cells = row.cells;
    cells[0].textContent = data.id;
    cells[1].textContent = data.name;
    cells[2].textContent = data.date;
    cells[3].textContent = data.time;

    cells[4].innerHTML = '';
    cells[4].appendChild(createActionButton('삭제', 'btn-danger', deleteRow));

    isEditing = false;
}

function deleteRow(event) {
    const row = event.target.closest('tr');
    const reservationId = row.cells[0].textContent;

    requestDelete(reservationId)
        .then(() => row.remove())
        .catch(error => console.error('Error:', error));
}

function requestCreate(reservation) {
    const requestOptions = {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(reservation)
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
