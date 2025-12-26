let selectedDate = '';
let selectedThemeId = '';
let selectedTimeId = '';

document.addEventListener('DOMContentLoaded', function() {
    flatpickr("#inline-calendar", {
        inline: true,
        locale: "ko",
        dateFormat: "Y-m-d",
        minDate: "today",
        onChange: function(selectedDates, dateStr) {
            selectedDate = dateStr;
            console.log("날짜 선택:", selectedDate);
        }
    });

    setupThemeSelection();

    fetchTimes();

    const reserveBtn = document.getElementById('add-reservation');
    if (reserveBtn) {
        reserveBtn.addEventListener('click', submitReservation);
    }
});

function setupThemeSelection() {
    const themeList = document.getElementById('theme-list');
    if (themeList) {
        const buttons = themeList.querySelectorAll('.list-group-item');
        buttons.forEach(btn => {
            btn.addEventListener('click', function() {
                buttons.forEach(b => b.classList.remove('active-theme'));
                this.classList.add('active-theme');
                selectedThemeId = this.getAttribute('data-id');
                console.log("테마 선택:", selectedThemeId);
            });
        });
    }
}

function fetchTimes() {
    fetch('/times')
        .then(response => {
            if (response.ok) return response.json();
            throw new Error('시간 로딩 실패');
        })
        .then(data => {
            const timeList = document.getElementById('time-list');
            timeList.innerHTML = '';

            data.forEach(time => {
                const btn = document.createElement('button');
                btn.type = 'button';
                btn.className = 'list-group-item list-group-item-action';
                btn.textContent = time.time;
                btn.dataset.id = time.id;

                btn.addEventListener('click', function() {
                    timeList.querySelectorAll('.list-group-item').forEach(item => {
                        item.classList.remove('active-theme');
                    });
                    this.classList.add('active-theme');
                    selectedTimeId = this.dataset.id;
                    console.log("시간 선택:", selectedTimeId);
                });

                timeList.appendChild(btn);
            });
        })
        .catch(error => console.error(error));
}

function submitReservation() {
    if (!selectedDate || !selectedThemeId || !selectedTimeId) {
        alert("날짜, 테마, 시간을 모두 선택해주세요.");
        return;
    }

    const reservationData = {
        date: selectedDate,
        timeId: selectedTimeId,
        themeId: selectedThemeId
    };

    const nameInput = document.getElementById('name-input');
    if (nameInput && nameInput.value.trim() !== "") {
        reservationData.name = nameInput.value.trim();
    }

    fetch('/reservations', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(reservationData),
    })
        .then(response => {
            if (response.status === 201) {
                alert("예약 완료!");
                window.location.href = "/reservation";
            } else {
                return response.text().then(text => alert(text));
            }
        })
        .catch(error => {
            console.error(error);
            alert("오류 발생");
        });
}
