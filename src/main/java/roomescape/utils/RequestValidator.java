package roomescape.utils;

import java.util.List;
import roomescape.dto.ReservationRequestDto;
import roomescape.exception.ErrorMessage;
import roomescape.exception.ReservationException;
import roomescape.model.Reservation;

public class RequestValidator {

    public static void validateSaveRequest(ReservationRequestDto dto) {
        if (dto.getDate() == null) {
            throw new ReservationException(ErrorMessage.EMPTY_DATE);
        }
        if (dto.getName().isEmpty()) {
            throw new ReservationException(ErrorMessage.EMPTY_NAME);
        }
        if (dto.getTime() == null) {
            throw new ReservationException(ErrorMessage.EMPTY_TIME);
        }
    }

    public static void validateDeleteRequest(List<Reservation> reservations, Long deletedId) {
        for (Reservation reservation : reservations) {
            if (reservation.getId().equals(deletedId)) {
                return ;
            }
        }
        throw new ReservationException(ErrorMessage.INVALID_ID_REQUEST);
    }

}
