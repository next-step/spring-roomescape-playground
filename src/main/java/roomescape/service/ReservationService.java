package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ReservationService {
    private final List<Reservation> reservations;
    private final AtomicLong index;

    public ReservationService() {
        this.reservations = setDefaultReservations();
        index = new AtomicLong(this.reservations.size() + 1);
    }

    public List<ReservationResponse> getAllReservations() {
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public List<Reservation> setDefaultReservations() {
        return new ArrayList<>(List.of(
                new Reservation(1L, "브라운", LocalDate.of(2026, 5, 5), LocalTime.of(10, 0)),
                new Reservation(2L, "조이", LocalDate.of(2026, 5, 6), LocalTime.of(13, 0)),
                new Reservation(3L, "포비", LocalDate.of(2026, 5, 7), LocalTime.of(15, 0))
        ));
    }

    public ReservationResponse createReservation(ReservationRequest request) {
        long id = index.getAndIncrement();
        Reservation reservation = new Reservation(id, request.name(), request.date(), request.time());

        reservations.add(reservation);

        return ReservationResponse.from(reservation);
    }

    public void deleteReservation(Long id) {
        reservations.removeIf(reservation -> Objects.equals(reservation.getId(), id));
    }
}
