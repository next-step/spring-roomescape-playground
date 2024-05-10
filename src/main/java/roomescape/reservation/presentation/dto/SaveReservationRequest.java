package roomescape.reservation.presentation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.presentation.exception.BadReservationSaveException;

public record SaveReservationRequest(
        String name,
        String date,
        String time
) {

    public SaveReservationRequest {
        validate();
    }

    private void validate() {
        validateBlanks(name, date, time);
    }

    private void validateBlanks(String... fields) {
        boolean isBlankFieldExist = Arrays.stream(fields)
                .anyMatch(String::isBlank);
        if (isBlankFieldExist) {
            throw new BadReservationSaveException("빈 값이 입력되었습니다.");
        }
    }

    public Reservation toReservation() {
        LocalDateTime reservationDateTime = LocalDateTime.of(
                LocalDate.parse(date),
                LocalTime.parse(time)
        );
        return new Reservation(name, reservationDateTime);
    }
}
