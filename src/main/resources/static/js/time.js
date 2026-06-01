(function () {
    let isTimeEditing = false;
    const TIME_API_ENDPOINT = '/times';

    document.addEventListener('DOMContentLoaded', () => {
        const addTimeBtn = document.getElementById('add-time');
        if (addTimeBtn) addTimeBtn.addEventListener('click', addTimeEditableRow);
        fetchTimesList();
    });

    function addTimeEditableRow() {
        if (isTimeEditing) return;

        const tableBody = document.getElementById('time-table-body');
        if (!tableBody) return;
        const row = tableBody.insertRow();
        isTimeEditing = true;

        row.insertCell(0).textContent = '';

        const timeInput = document.createElement('input');
        timeInput.type = 'time';
        timeInput.className = 'form-control';
        row.insertCell(1).appendChild(timeInput);

        const actionCell = row.insertCell(2);
        actionCell.appendChild(createTimeActionButton('확인', 'btn-primary', (e) => saveTimeRowData(e, row, timeInput)));
        actionCell.appendChild(createTimeActionButton('취소', 'btn-secondary', () => {
            row.remove();
            isTimeEditing = false;
        }));
    }

    function createTimeActionButton(label, className, eventListener) {
        const button = document.createElement('button');
        button.textContent = label;
        button.classList.add('btn', className, 'mr-2');
        button.addEventListener('click', eventListener);
        return button;
    }

    function saveTimeRowData(event, row, timeInput) {
        if (!timeInput || !timeInput.value) {
            alert("시간을 입력해주세요.");
            return;
        }

        // 백엔드 Jackson LocalTime 역직렬화 규격에 맞게 전송 ("HH:mm" 형태로 패킹)
        const timeValue = timeInput.value.substring(0, 5);

        requestCreateTime(timeValue)
            .then(() => {
                isTimeEditing = false;
                fetchTimesList(); // 전체 스냅샷 새로고침 적용
            })
            .catch(error => console.error('Error:', error));
    }

    function fetchTimesList() {
        requestReadTimes()
            .then(renderTimesTable)
            .catch(error => console.error('Error fetching times:', error));
    }

    function renderTimesTable(data) {
        const tableBody = document.getElementById('time-table-body');
        if (!tableBody) return;
        tableBody.innerHTML = '';

        data.forEach(time => {
            const row = tableBody.insertRow();
            insertTimeRowData(row, time);
        });
    }

    function insertTimeRowData(row, time) {
        // 백엔드 응답이 순수 문자열 배열이거나 시간풀 객체일 경우 통합 매핑
        if (typeof time === 'string') {
            row.insertCell(0).textContent = '-';
            row.insertCell(1).textContent = time.substring(0, 5);

            const actionCell = row.insertCell(2);
            actionCell.appendChild(createTimeActionButton('삭제', 'btn-danger', () => deleteTimeRowData(time, row)));
        } else {
            row.insertCell(0).textContent = time.id || '-';
            row.insertCell(1).textContent = time.time ? time.time.substring(0, 5) : '-';

            const actionCell = row.insertCell(2);
            actionCell.appendChild(createTimeActionButton('삭제', 'btn-danger', () => deleteTimeRowData(time.id, row)));
        }
    }

    function deleteTimeRowData(id, row) {
        if (confirm("해당 시간대를 가용 풀에서 삭제하시겠습니까?\n(⚠️ CASCADE 설정에 의해 연관된 예약이 같이 지워질 수 있습니다.)")) {
            requestDeleteTime(id)
                .then(() => row.remove())
                .catch(error => console.error('Error:', error));
        }
    }

    function requestCreateTime(timeValue) {
        return fetch(TIME_API_ENDPOINT, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(timeValue)
        }).then(response => {
            if (response.status === 201) return response.json();
            throw new Error('Create failed');
        });
    }

    function requestReadTimes() {
        return fetch(TIME_API_ENDPOINT)
            .then(response => {
                if (response.status === 200) return response.json();
                throw new Error('Read failed');
            });
    }

    function requestDeleteTime(id) {
        return fetch(`${TIME_API_ENDPOINT}/${id}`, {method: 'DELETE'})
            .then(response => {
                if (response.status !== 204) throw new Error('Delete failed');
            });
    }
})();
