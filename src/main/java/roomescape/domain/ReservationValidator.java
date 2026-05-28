package roomescape.domain;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class ReservationValidator {

    private static final int MIN_RESERVATION_HOUR = 0;
    private static final int MAX_RESERVATION_HOUR = 23; 

    public void validateReservationDateTime(LocalDate date, String time) {
        LocalTime reservationTime = LocalTime.parse(time);

        validateReservationHour(reservationTime.getHour());

        LocalDateTime reservationDateTime = LocalDateTime.of(date, reservationTime);
        LocalDateTime now = LocalDateTime.now();

        if (reservationDateTime.isBefore(now)) {
            throw new IllegalArgumentException("지나간 시간은 예약할 수 없습니다.");
        }
    }

    private void validateReservationHour(int hour) {
        if (hour < MIN_RESERVATION_HOUR || hour > MAX_RESERVATION_HOUR) {
            throw new IllegalArgumentException("예약 시간은 0시부터 23시 사이여야 합니다.");
        }
    }
}
