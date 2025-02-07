package roomescape.reservation.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import roomescape.global.exception.code.ErrorStatus;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.exception.InvalidParameterException;
import roomescape.reservation.exception.ReservationNotFoundException;
import roomescape.reservation.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponse> getReservationResponses() {
        List<ReservationResponse> reservationResponses = reservationRepository.getReservations()
                .stream()
                .map(ReservationResponse::fromReservation)
                .collect(Collectors.toList());

        return reservationResponses;
    }

    public ReservationResponse createReservation(final ReservationRequest reservationRequest) {
        if (!reservationRequest.isValid()) {
            throw new InvalidParameterException(ErrorStatus.INVALID_REQUEST_RESERVATION_INFO);
        }

        Reservation savedReservation = reservationRepository.addReservation(
                ReservationRequest.toReservation(reservationRequest));

        return ReservationResponse.fromReservation(savedReservation);
    }

    public void deleteReservation(final Long id) {
        if (id < 0) {
            throw new InvalidParameterException(ErrorStatus.INVALID_REQUEST_RESERVATION_ID);
        }

        boolean removed = reservationRepository.removeReservation(id);

        if (!removed) {
            throw new ReservationNotFoundException(ErrorStatus.RESERVATION_NOT_FOUND, id);
        }
    }
}
