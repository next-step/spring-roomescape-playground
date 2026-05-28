let isEditing = false;
const RESERVATION_API_ENDPOINT = '/reservations';
const TIME_API_ENDPOINT = '/times';

const GLOBAL_ALL_TIMES = [
    "08:00", "09:00", "10:00", "11:00", "12:00", "13:00",
    "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00"
];

document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('add-reservation').addEventListener('click', addEditableRow);
    fetchReservations();
});

function fetchTimesByDate(selectedDate, timeSelectElement) {
    timeSelectElement.innerHTML = '<option value="">로딩 중...</option>';

    fetch(`${TIME_API_ENDPOINT}?date=${selectedDate}`)
        .then(response => {
            if (response.status === 200) return response.json();
            throw new Error('Read failed');
        })
        .then(invalidTimes => {
            const formattedInvalidTimes = invalidTimes.map(time => {
                if (typeof time === 'string') {
                    return time.substring(0, 5); // "08:00:00" -> "08:00"
                }
                if (typeof time === 'object' && time !== null && time.time) {
                    return time.time.substring(0, 5);
                }
                return time;
            });

            timeSelectElement.innerHTML = '<option value="">시간 선택</option>';

            GLOBAL_ALL_TIMES.forEach(timeValue => {

                if (!formattedInvalidTimes.includes(timeValue)) {
                    const option = document.createElement('option');
                    option.value = timeValue;
                    option.textContent = timeValue;
                    timeSelectElement.appendChild(option);
                }
            });

            if (timeSelectElement.options.length === 1) {
                timeSelectElement.innerHTML = '<option value="">선택 가능한 시간이 없습니다</option>';
            }
        })
        .catch(error => {
            console.error('시간 데이터를 필터링하는 중 오류 발생:', error);
            timeSelectElement.innerHTML = '<option value="">데이터 로드 실패</option>';
        });
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

    row.insertCell(3).textContent = (typeof reservation.time === 'object' && reservation.time !== null)
        ? reservation.time.time
        : reservation.time;

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

function createEditableFieldsFor(row) {
    const nameInput = createInput('text');
    const dateInput = createInput('date');

    const timeDropdown = document.createElement('select');
    timeDropdown.className = 'form-control';
    timeDropdown.innerHTML = '<option value="">날짜를 먼저 선택하세요</option>';

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

    if (!timeSelect.value) {
        alert("시간을 선택해주세요.");
        return;
    }

    const reservation = {
        name: nameInput.value,
        date: dateInput.value,
        time: timeSelect.value
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
    cells[3].textContent = (typeof data.time === 'object' && data.time !== null) ? data.time.time : data.time;

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
