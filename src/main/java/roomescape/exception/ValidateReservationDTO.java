package roomescape.exception;

import roomescape.dto.ReservationDto;

import static io.micrometer.common.util.StringUtils.isBlank;

public class ValidateReservationDTO {
    public static void validateReservation(ReservationDto reservation) {
        if (isBlank(reservation.getName()) || isBlank(reservation.getDate()) || isBlank(reservation.getTime())) {
            throw new BadRequestException("Name, date, and time are required for reservation creation");
        }
    }
}
