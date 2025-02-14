package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.request.CreateReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.entity.Reservation;
import roomescape.global.exception.BadRequestException;
import roomescape.repository.ReservationRepository;

import java.util.ArrayList;
import java.util.List;

import static roomescape.global.exception.ExceptionMessage.RESERVATION_NOT_EXISTS;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final List<Reservation> reservations = new ArrayList<>();

    public ReservationService(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public ReservationResponse createReservation(final CreateReservationRequest request) {
        Reservation reservation = new Reservation(request.name(), request.date(), request.time());
        reservations.add(reservation);
        return new ReservationResponse(reservation);
    }

    public List<ReservationResponse> getReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationResponse::new)
                .toList();
    }

    public void deleteReservation(final long reservationId) {
        Reservation reservation = findReservation(reservationId);
        reservations.remove(reservation);
    }

    private Reservation findReservation(final long reservationId) {
        return reservations.stream()
                .filter(reservation -> reservation.isSameId(reservationId))
                .findFirst()
                .orElseThrow(() -> new BadRequestException(RESERVATION_NOT_EXISTS.getMessage()));
    }
}
