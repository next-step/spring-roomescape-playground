package roomescape.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.customexception.AlreadyReservedException;
import roomescape.exception.customexception.ReservationNotFoundException;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        Reservation reservation = reservationRequest.toReservation(nextId());
        checkConflict(reservation);
        reservations.add(reservation);
        return ReservationResponse.fromReservation(reservation);
    }

    public List<ReservationResponse> readAllReservations() {
        List<Reservation> reservations = reservationRepository.findAllReservations();
        return reservations.stream()
                .map(ReservationResponse::fromReservation)
                .toList();
    }

    public void deleteReservation(Long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(ReservationNotFoundException::new);
        reservations.remove(reservation);
    }

    private void checkConflict(Reservation newReservation) {
        if (reservations.stream().anyMatch(reservation -> reservation.conflicts(newReservation))) {
            idCounter.decrementAndGet();
            throw new AlreadyReservedException();
        }
    }

    private Long nextId() {
        return idCounter.getAndIncrement();
    }
}
