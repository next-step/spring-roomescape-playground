package roomescape.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Component;
import roomescape.dao.ReservationDao;

@Component
public class ValidTimeChecker {
    private final ReservationDao reservationDao;

    public ValidTimeChecker(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public void checkReservationable(LocalDate requestDate, LocalTime requestTime) {
        checkValidTimeByDate(requestDate, requestTime);
    }

    private void checkValidTimeByDate(LocalDate requestDate, LocalTime requestTime) {
        List<LocalTime> invalidTimes = reservationDao.findAllReservationTimesByDate(requestDate);

        boolean isInvalid = invalidTimes.stream()
                .anyMatch(invalidTime -> invalidTime.equals(requestTime));

        if (isInvalid) {
            throw new ReservationException.InvalidReservationException("선택하신 시간은 예약할 수 없는 시간입니다.");
        }
    }
}
