package roomescape.exception;

import roomescape.dto.ReservationRequestDto;

import static io.micrometer.common.util.StringUtils.isBlank;

public class ValidateReservationDTO {
    public static void validateReservation(ReservationRequestDto reservation) {
        if (isBlank(reservation.name()) || isBlank(reservation.date()) || reservation.timeId() == null) {
            throw new BadRequestException("Name, date, and time are required for reservation creation");
        }
    }
}
