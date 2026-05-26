let isEditing = false;
const TIME_API_ENDPOINT = '/times';

document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('add-time').addEventListener('click', addEditableRow);
    fetchTimes();
});

function addEditableRow() {
    if (isEditing) return;

    const tableBody = document.getElementById('time-table-body');
    const row = tableBody.insertRow();
    isEditing = true;

    createEditableFieldsFor(row);
    addSaveAndCancelButtonsToRow(row);
}

function createEditableFieldsFor(row) {
    row.insertCell(0).textContent = '';

    const timeInput = createInput('time');
    row.insertCell(1).appendChild(timeInput);
}

function createInput(type) {
    const input = document.createElement('input');
    input.type = type;
    input.className = 'form-control';
    return input;
}

function addSaveAndCancelButtonsToRow(row) {
    const actionCell = row.insertCell(2);
    actionCell.appendChild(createActionButton('확인', 'btn-primary', saveRow));
    actionCell.appendChild(createActionButton('취소', 'btn-secondary', () => {
        row.remove();
        isEditing = false;
    }));
}

function createActionButton(label, className, eventListener) {
    const button = document.createElement('button');
    button.textContent = label;
    button.classList.add('btn', className, 'mr-2');
    button.addEventListener('click', eventListener);
    return button;
}

function saveRow(event) {
    const row = event.target.parentNode.parentNode;
    const timeInput = row.cells[1].querySelector('input'); // 2번째 칸의 input창 지정

    if (!timeInput || !timeInput.value) {
        alert("시간을 입력해주세요.");
        return;
    }

    const time = {
        time: timeInput.value
    };

    requestCreate(time)
        .then(data => updateRowWithTimeData(row, data))
        .catch(error => console.error('Error:', error));

    isEditing = false;
}

function updateRowWithTimeData(row, data) {
    const cells = row.cells;
    cells[0].textContent = data.id;
    cells[1].textContent = data.time;
    cells[2].innerHTML = '';
    cells[2].appendChild(createActionButton('삭제', 'btn-danger', deleteRow));

    isEditing = false;
}

function deleteRow(event) {
    const row = event.target.closest('tr');
    const id = row.cells[0].textContent;

    requestDelete(id)
        .then(() => row.remove())
        .catch(error => console.error('Error:', error));
}

function fetchTimes() {
    requestRead()
        .then(renderTimes)
        .catch(error => console.error('Error fetching times:', error));
}

function renderTimes(data) {
    const tableBody = document.getElementById('time-table-body');
    tableBody.innerHTML = '';

    data.forEach(time => {
        const row = tableBody.insertRow();
        insertTimeRow(row, time);
    });
}

function insertTimeRow(row, time) {
    if (typeof time === 'string') {
        row.insertCell(0).textContent = '-';
        row.insertCell(1).textContent = time;
    } else {
        row.insertCell(0).textContent = time.id;
        row.insertCell(1).textContent = time.time;
    }

    const actionCell = row.insertCell(2);
    actionCell.appendChild(createActionButton('삭제', 'btn-danger', deleteRow));
}

function requestCreate(time) {
    const requestOptions = {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(time)
    };

    return fetch(TIME_API_ENDPOINT, requestOptions)
        .then(response => {
            if (response.status === 201) return response.json();
            throw new Error('Create failed');
        });
}

function requestRead() {
    return fetch(TIME_API_ENDPOINT)
        .then(response => {
            if (response.status === 200) return response.json();
            throw new Error('Read failed');
        });
}

function requestDelete(id) {
    const requestOptions = {
        method: 'DELETE',
    };

    return fetch(`${TIME_API_ENDPOINT}/${id}`, requestOptions)
        .then(response => {
            if (response.status !== 204) throw new Error('Delete failed');
        });
}
