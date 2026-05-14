package roomescape.domain;

import org.springframework.stereotype.Component;
import roomescape.dto.ReservationRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ReservationValidator {

    private static final int MIN_RESERVATION_HOUR = 0;
    private static final int MAX_RESERVATION_HOUR = 24;

    public void validateReservationTime(ReservationRequest request) {
        LocalDate reservationDate = LocalDate.parse(request.date(), DateTimeFormatter.ISO_LOCAL_DATE);
        String[] timeParts = request.time().split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);

        validateReservationHour(hour);
        LocalDateTime reservationDateTime = LocalDateTime.of(reservationDate, LocalTime.of(0, minute))
                .plusHours(hour);
        LocalDateTime now = LocalDateTime.now();

        if (reservationDateTime.isBefore(now)) {
            throw new IllegalArgumentException("지나간 시간은 예약할 수 없습니다.");
        }
    }

    private void validateReservationHour(int hour) {
        if (hour < MIN_RESERVATION_HOUR || hour > MAX_RESERVATION_HOUR) {
            throw new IllegalArgumentException("예약 시간은 0시부터 24시 사이여야 합니다.");
        }
    }

    public void validateDuplicatedReservation(ReservationRequest request, List<Reservation> reservations) {
        boolean isDuplicated = reservations.stream()
                .anyMatch(reservation -> 
                    reservation.getDate().equals(request.date()) && 
                    reservation.getTime().equals(request.time())
                );

        if (isDuplicated) {
            throw new IllegalArgumentException("이미 예약된 시간입니다.");
        }
    }
}
