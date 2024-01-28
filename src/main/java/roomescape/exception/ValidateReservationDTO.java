package roomescape.exception;

import roomescape.dto.ReservationRequestDto;

import static io.micrometer.common.util.StringUtils.isBlank;

public class ValidateReservationDTO {
    public static void validateReservation(ReservationRequestDto reservation) {
        if (isBlank(reservation.getName()) || isBlank(reservation.getDate()) || isBlank(reservation.getTime())) {
            throw new BadRequestException("Name, date, and time are required for reservation creation");
        }
    }
}
