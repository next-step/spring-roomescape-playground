package roomescape.validator;

import roomescape.dto.Reservation;
import roomescape.exception.InvalidException;

public class ReservationValidator {

    public static void validate(Reservation reservation) {
        if (reservation.getName() == null || reservation.getName().trim().isEmpty()) {
            throw new InvalidException("Name is required");
        }

        if (reservation.getDate() == null) {
            throw new InvalidException("Date is required");
        }

        if (reservation.getTime() == null) {
            throw new InvalidException("Time is required");
        }
    }

}
