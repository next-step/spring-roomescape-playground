package roomescape.reservation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.exception.customexception.ReservationConflictException;
import roomescape.exception.customexception.ReservationNotFoundException;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.time.domain.Time;
import roomescape.time.repository.TimeRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(ReservationRepository reservationRepository, TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        Time time = timeRepository.findTimeById(reservationRequest.time());
        Reservation reservation = reservationRequest.toReservation(time);
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
        if (reservationRepository.countConflictingReservations(newReservation.getDate(), newReservation.getTime())
                > 0) {
            throw new ReservationConflictException();
        }
    }
}
