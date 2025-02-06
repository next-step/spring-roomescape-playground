package roomescape.validator;

import roomescape.entity.Reservation;
import roomescape.exception.InvalidReservationException;
import roomescape.exception.NotFoundReservationException;

public class ReservationValidator {

    public static void validate(Reservation reservation) {
        if (reservation.getName() == null || reservation.getName().trim().isEmpty()) {
            throw new InvalidReservationException("Name is required");
        }

        if (reservation.getDate() == null) {
            throw new NotFoundReservationException("Date is required");
        }

        if (reservation.getTime() == null) {
            throw new NotFoundReservationException("Time is required");
        }
    }

    public static void deleteValidate(boolean removed, int id){
        if (!removed) {
            throw new NotFoundReservationException("Reservation with ID" + id + "Not Found");
        }
    }
}
