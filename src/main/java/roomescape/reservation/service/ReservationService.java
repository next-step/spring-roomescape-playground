package roomescape.reservation.service;

import static roomescape.global.exception.code.ErrorStatus.INVALID_REQUEST_RESERVATION_ID;
import static roomescape.global.exception.code.ErrorStatus.INVALID_REQUEST_RESERVATION_INFO;
import static roomescape.global.exception.code.ErrorStatus.RESERVATION_NOT_FOUND;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.exception.InvalidParameterException;
import roomescape.reservation.exception.ReservationNotFoundException;

@Service
public class ReservationService {

    public List<ReservationResponse> getReservationResponses(List<Reservation> reservations) {
        List<ReservationResponse> reservationResponses = reservations.stream()
                .map(ReservationResponse::fromReservation)
                .collect(Collectors.toList());

        return reservationResponses;
    }

    public ReservationResponse createReservation(ReservationRequest reservationRequest,
                                                 List<Reservation> reservations, Long id) {
        if (!reservationRequest.isValid()) {
            throw new InvalidParameterException(INVALID_REQUEST_RESERVATION_INFO);
        }

        Reservation newReservation = ReservationRequest.toReservation(reservationRequest, id);
        reservations.add(newReservation);

        return ReservationResponse.fromReservation(newReservation);
    }

    public void deleteReservation(List<Reservation> reservations, Long id) {
        if (id < 0) {
            throw new InvalidParameterException(INVALID_REQUEST_RESERVATION_ID);
        }

        boolean removed = reservations.removeIf(reservation -> reservation.getId().equals(id));

        if (!removed) {
            throw new ReservationNotFoundException(RESERVATION_NOT_FOUND);
        }
    }
}
