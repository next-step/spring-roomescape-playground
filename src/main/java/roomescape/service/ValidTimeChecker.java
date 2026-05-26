package roomescape.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import roomescape.DAO.TimeDao;

public class ValidTimeChecker {
    private final TimeDao timeDao;

    public ValidTimeChecker(TimeDao timedao) {
        this.timeDao = timedao;
    }

    public void checkReservationable(LocalDate requestDate, LocalTime requestTime) {
        checkValidDateRange(requestDate);

        checkValidTimeByDate(requestDate, requestTime);
    }

    private void checkValidDateRange(LocalDate requestDate) {
        LocalDate today = LocalDate.now();
        LocalDate maxAvailableDate = today.plusDays(7);
        if (requestDate.isBefore(today) || requestDate.isAfter(maxAvailableDate)) {
            throw new ReservationException.InvalidReservationException("예약은 오늘부터 일주일 뒤까지만 가능합니다.");
        }
    }

    private void checkValidTimeByDate(LocalDate requestDate, LocalTime requestTime) {
        List<LocalTime> validTimes = timeDao.findValidTimesByDate(requestDate);

        boolean isValid = validTimes.stream()
                .anyMatch(validTime -> validTime.equals(requestTime));

        if (!isValid) {
            throw new ReservationException.InvalidReservationException("선택하신 시간은 예약할 수 없는 시간입니다.");
        }
    }
}
