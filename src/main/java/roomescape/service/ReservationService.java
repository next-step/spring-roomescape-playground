package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.request.CreateReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.global.exception.BadRequestException;
import roomescape.repository.ReservationRepository;

import java.util.List;

import static roomescape.global.exception.ExceptionMessage.RESERVATION_ALREADY_EXISTS;
import static roomescape.global.exception.ExceptionMessage.RESERVATION_NOT_EXISTS;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public ReservationResponse createReservation(final CreateReservationRequest request) {
        Reservation reservation = request.toReservation();
        validateAlreadyOccupied(reservation);
        Reservation reservationWithId = reservationRepository.save(reservation);
        return new ReservationResponse(reservationWithId);
    }

    private void validateAlreadyOccupied(final Reservation reservation) {
        if (reservationRepository.existsByDateAndTime(reservation.getDate(), reservation.getTime())) {
            throw new BadRequestException(RESERVATION_ALREADY_EXISTS.getMessage());
        }
    }

    public List<ReservationResponse> getReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationResponse::new)
                .toList();
    }

    public void deleteReservation(final long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new BadRequestException(RESERVATION_NOT_EXISTS.getMessage()));
        reservationRepository.deleteById(reservation.getId());
    }
}
