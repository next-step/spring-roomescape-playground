package roomescape.domain.reservation.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import roomescape.global.exception.code.ErrorStatus;
import roomescape.domain.reservation.domain.Reservation;
import roomescape.domain.reservation.dto.ReservationRequest;
import roomescape.domain.reservation.dto.ReservationResponse;
import roomescape.domain.reservation.exception.InvalidParameterException;
import roomescape.domain.reservation.exception.ReservationNotFoundException;
import roomescape.domain.reservation.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(final ReservationRepository reservationRepository) {
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
        Reservation savedReservation = reservationRepository.addReservation(reservationRequest);

        return ReservationResponse.fromReservation(savedReservation);
    }

    public void deleteReservation(final long id) {
        if (id < 0) {
            throw new InvalidParameterException(ErrorStatus.INVALID_REQUEST_RESERVATION_ID);
        }

        boolean removed = reservationRepository.removeReservation(id);

        if (!removed) {
            throw new ReservationNotFoundException(ErrorStatus.RESERVATION_NOT_FOUND,  id);
        }
    }
}
