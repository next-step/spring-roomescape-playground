package roomescape.DTO;

import roomescape.Model.Reservation;

import java.util.Arrays;

public record SaveReservationRequest(
        String name,
        String date,
        String time
) {
    public SaveReservationRequest {
        validate(name, date, time);
    }

    private void validate(String name, String date, String time) {
        validateBlanks(name, date, time);
    }

    private void validateBlanks(String... fields) {
        boolean isBlank = Arrays.stream(fields).anyMatch(String::isBlank);
        if (isBlank) {
            throw new IllegalArgumentException("값이 입력되지 않았습니다");
        }
    }

    public Reservation toReservation() {
        return new Reservation(name, date, time);
    }
}
