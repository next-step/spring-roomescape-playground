package roomescape.utils;

import java.util.List;
import roomescape.exception.ErrorMessage;
import roomescape.exception.ReservationException;
import roomescape.model.Reservation;

public class RequestValidator {

    public static void validateDeleteRequest(List<Reservation> reservations, Long deletedId) {
        for (Reservation reservation : reservations) {
            if (reservation.getId().equals(deletedId)) {
                return ;
            }
        }
        throw new ReservationException(ErrorMessage.INVALID_ID_REQUEST);
    }

}
