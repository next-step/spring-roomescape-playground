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

    public void validateReservationTime(ReservationRequest request) {
        LocalDate reservationDate = LocalDate.parse(request.getDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalTime reservationTime = LocalTime.parse(request.getTime(), DateTimeFormatter.ofPattern("HH:mm"));
        LocalDateTime reservationDateTime = LocalDateTime.of(reservationDate, reservationTime);
        LocalDateTime now = LocalDateTime.now();

        if (reservationDateTime.isBefore(now)) {
            throw new IllegalArgumentException("지나간 시간은 예약할 수 없습니다.");
        }
    }

    public void validateDuplicatedReservation(ReservationRequest request, List<Reservation> reservations) {
        boolean isDuplicated = reservations.stream()
                .anyMatch(reservation -> 
                    reservation.getDate().equals(request.getDate()) && 
                    reservation.getTime().equals(request.getTime())
                );

        if (isDuplicated) {
            throw new IllegalArgumentException("이미 예약된 시간입니다.");
        }
    }
}
