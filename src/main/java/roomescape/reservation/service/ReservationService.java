package roomescape.reservation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.exception.customexception.AlreadyReservedException;
import roomescape.exception.customexception.ReservationNotFoundException;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.repository.ReservationRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public synchronized ReservationResponse createReservation(ReservationRequest reservationRequest) {
        Reservation reservation = reservationRequest.toReservation();
        checkConflict(reservation);
        Reservation createdReservation = reservationRepository.saveReservation(reservation);
        return ReservationResponse.fromReservation(createdReservation);
    }

    public List<ReservationResponse> readAllReservations() {
        List<Reservation> reservations = reservationRepository.findAllReservations();
        return reservations.stream()
                .map(ReservationResponse::fromReservation)
                .toList();
    }

    public void deleteReservation(Long id) {
        int affectedRows = reservationRepository.deleteReservationById(id);
        if (affectedRows == 0) {
            throw new ReservationNotFoundException();
        }
    }

    private void checkConflict(Reservation newReservation) {
        if (reservationRepository.countConflictingReservations(newReservation.getDateTime()) > 0) {
            throw new AlreadyReservedException();
        }
    }
}
