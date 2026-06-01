(function () {
    let isReservationEditing = false;
    const RESERVATION_API_ENDPOINT = '/reservations';
    const TIME_API_ENDPOINT = '/times';

    const GLOBAL_ALL_TIMES = [
        "08:00", "09:00", "10:00", "11:00", "12:00", "13:00",
        "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00"
    ];

    let activeTimesList = [...GLOBAL_ALL_TIMES];

    document.addEventListener('DOMContentLoaded', () => {
        const addBtn = document.getElementById('add-reservation');
        if (addBtn) addBtn.addEventListener('click', addReservationEditableRow);

        // 🎯 [기능 추가] 시간 등록 버튼 이벤트 바인딩
        const submitTimeBtn = document.getElementById('submit-new-time');
        if (submitTimeBtn) submitTimeBtn.addEventListener('click', submitNewAvailableTime);

        // 필수 파라미터 규격을 준수하여 최초 데이터 로드
        loadSystemAvailableTimes("2026-05-31").then(() => {
            fetchReservations();
        });
    });

    function loadSystemAvailableTimes(targetDate) {
        return fetch(`${TIME_API_ENDPOINT}?date=${targetDate}`)
            .then(response => {
                if (response.status === 200) return response.json();
                throw new Error('Times load failed');
            })
            .then(invalidTimes => {
                renderAvailableTimeBadges();
            })
            .catch(error => console.error('시간 풀 로드 오류:', error));
    }

    // 상단에 가용 시간 배지를 그리는 함수
    function renderAvailableTimeBadges() {
        const container = document.getElementById('available-times-container');
        if (!container) return;
        container.innerHTML = '';

        if (activeTimesList.length === 0) {
            container.innerHTML = '<small class="text-muted">가용 시간대가 비어있습니다. 시간을 추가하세요.</small>';
            return;
        }

        activeTimesList.forEach((timeValue, index) => {
            const badge = document.createElement('span');
            badge.className = 'time-badge';
            badge.innerHTML = `${timeValue} <span class="delete-time-btn" title="삭제">&times;</span>`;

            badge.querySelector('.delete-time-btn').addEventListener('click', () => {
                deleteSystemAvailableTime(index, timeValue);
            });
            container.appendChild(badge);
        });
    }

    function submitNewAvailableTime() {
        const timeInput = document.getElementById('new-available-time');
        if (!timeInput || !timeInput.value) {
            alert("추가할 시간을 선택해 주세요.");
            return;
        }

        const timeValue = timeInput.value.substring(0, 5); // "HH:mm"
        const minutes = timeValue.split(':')[1];

        if (minutes !== '00') {
            alert("⚠️ 예약 가능 시간은 오직 00분 단위(정각)로만 등록할 수 있습니다.");
            return;
        }

        if (activeTimesList.includes(timeValue)) {
            alert("이미 가용 시간 풀에 등록되어 있는 시간대입니다.");
            return;
        }

        fetch(TIME_API_ENDPOINT, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(timeValue)
        })
            .then(response => {
                if (response.status === 201 || response.status === 200) {
                    activeTimesList.push(timeValue);
                    activeTimesList.sort((a, b) => a.localeCompare(b));

                    renderAvailableTimeBadges();
                    timeInput.value = ''; // 인풋창 비우기
                    alert(`[${timeValue}] 정각 시간대가 데이터베이스와 가용 풀에 최종 등록되었습니다.`);
                    return;
                }
                throw new Error('Create time API failed');
            })
            .catch(error => {
                console.error('시간 생성 에러:', error);
                alert('서버 저장 실패: 이미 DB에 존재하는 시간이거나 형식 오류입니다.');
            });
    }

    function deleteSystemAvailableTime(index, timeString) {
        if (!confirm(`[${timeString}] 시간대를 가용 예약 풀에서 제외하시겠습니까?\n(⚠️ 해당 시간대의 예약 선택이 불가능해집니다.)`)) {
            return;
        }

        activeTimesList.splice(index, 1);
        renderAvailableTimeBadges();
        alert(`[${timeString}] 시간대가 정상적으로 제외되었습니다.`);
    }

    function fetchTimesByDate(selectedDate, timeSelectElement) {
        timeSelectElement.innerHTML = '<option value="">로딩 중...</option>';

        fetch(`${TIME_API_ENDPOINT}?date=${selectedDate}`)
            .then(response => {
                if (response.status === 200) return response.json();
                throw new Error('Read failed');
            })
            .then(invalidTimes => {
                const formattedInvalidTimes = invalidTimes.map(time => {
                    if (typeof time === 'string') return time.substring(0, 5);
                    if (typeof time === 'object' && time !== null && time.time) return time.time.substring(0, 5);
                    return time;
                });

                timeSelectElement.innerHTML = '<option value="">시간 선택</option>';

                activeTimesList.forEach(timeValue => {
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
                console.error('시간 필터링 중 에러:', error);
                timeSelectElement.innerHTML = '<option value="">데이터 로드 실패</option>';
            });
    }

    function fetchReservations() {
        requestRead()
            .then(renderReservations)
            .catch(error => console.error('Error fetching reservations:', error));
    }

    function renderReservations(data) {
        const tableBody = document.getElementById('reservation-table-body');
        if (!tableBody) return;
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
            ? reservation.time.time.substring(0, 5)
            : reservation.time.substring(0, 5);

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

    function addReservationEditableRow() {
        if (isReservationEditing) return;

        const tableBody = document.getElementById('reservation-table-body');
        const row = tableBody.insertRow();
        isReservationEditing = true;

        createEditableFieldsFor(row);
        addSaveAndCancelButtonsToRow(row);
    }

    function createEditableFieldsFor(row) {
        row.insertCell(0).textContent = '';
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

        row.insertCell(1).appendChild(nameInput);
        row.insertCell(2).appendChild(dateInput);
        row.insertCell(3).appendChild(timeDropdown);
    }

    function addSaveAndCancelButtonsToRow(row) {
        const actionCell = row.insertCell(4);
        actionCell.appendChild(createActionButton('확인', 'btn-primary', saveRow));
        actionCell.appendChild(createActionButton('취소', 'btn-secondary', () => {
            row.remove();
            isReservationEditing = false;
        }));
    }

    function validateFutureDate(dateString) {
        const inputDate = new Date(dateString);
        const today = new Date("2026-05-31");

        inputDate.setHours(0, 0, 0, 0);
        today.setHours(0, 0, 0, 0);

        return inputDate >= today;
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

        if (!validateFutureDate(dateInput.value)) {
            alert("이미 지난 날짜는 예약할 수 없습니다.");
            return;
        }

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

        isReservationEditing = false;
    }

    function updateRowWithReservationData(row, data) {
        const cells = row.cells;
        cells[0].textContent = data.id;
        cells[1].textContent = data.name;
        cells[2].textContent = data.date;
        cells[3].textContent = (typeof data.time === 'object' && data.time !== null) ? data.time.time.substring(0, 5) : data.time.substring(0, 5);

        cells[4].innerHTML = '';
        cells[4].appendChild(createActionButton('삭제', 'btn-danger', deleteRow));

        isReservationEditing = false;
    }

    function deleteRow(event) {
        const row = event.target.closest('tr');
        const reservationId = row.cells[0].textContent;

        requestDelete(reservationId)
            .then(() => row.remove())
            .catch(error => console.error('Error:', error));
    }

    function requestCreate(reservation) {
        return fetch(RESERVATION_API_ENDPOINT, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(reservation)
        }).then(response => {
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
        return fetch(`${RESERVATION_API_ENDPOINT}/${id}`, {method: 'DELETE'})
            .then(response => {
                if (response.status !== 204) throw new Error('Delete failed');
            });
    }
})();
