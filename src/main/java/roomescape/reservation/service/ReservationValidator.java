package roomescape.reservation.service;

import org.springframework.stereotype.Service;
import roomescape.reservation.exception.InvalidReservationRequestException;
import roomescape.reservation.request.ReservationRequest;

@Service
public class ReservationValidator {

    public void validate(ReservationRequest request) {
        if (request.name() == null || request.name().isEmpty()) {
            throw new InvalidReservationRequestException("Name is required");
        }
        if (request.date() == null) {
            throw new InvalidReservationRequestException("Date is required");
        }
        if (request.time() == null) {
            throw new InvalidReservationRequestException("Time is required");
        }
    }
}
