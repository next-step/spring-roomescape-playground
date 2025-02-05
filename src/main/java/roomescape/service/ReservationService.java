package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.request.CreateReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.entity.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ReservationService {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(0);

    public ReservationResponse createReservation(final CreateReservationRequest request) {
        Reservation reservation = new Reservation(index.incrementAndGet(), request.name(), request.date(), request.time());
        reservations.add(reservation);
        return ReservationResponse.create(
                reservation);
    }

    public List<ReservationResponse> getReservations() {
        return reservations.stream()
                .map(ReservationResponse::create)
                .toList();
    }

//    private List<Reservation> createReservations() {
//        return List.of(
//                new Reservation(1L, "브라운", LocalDate.of(2024, 2, 1), LocalTime.of(10, 0, 0)),
//                new Reservation(2L, "브라운", LocalDate.of(2024, 2, 2), LocalTime.of(11, 0, 0)),
//                new Reservation(3L, "브라운", LocalDate.of(2024, 2, 3), LocalTime.of(12, 0, 0))
//        );
//    }

    public void deleteReservation(final Long reservationId) {
        int index = getIndex(reservationId);
        reservations.remove(index);
    }

    private int getIndex(final Long reservationId) {
        int index = 0;
        for (Reservation reservation : reservations) {
            if (reservation.getId().equals(reservationId)) {
                break;
            }
            index++;
        }
        return index;
    }
}
