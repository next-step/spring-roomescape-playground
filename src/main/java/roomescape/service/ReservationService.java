package roomescape.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.error.ErrorMessage;
import roomescape.error.exception.InvalidValueException;

@Service
public class ReservationService {
    private AtomicLong index = new AtomicLong(0);
    private List<Reservation> reservations = new ArrayList<>();

    public ReservationResponse reserve(ReservationCreateRequest request) {
        Long id = index.incrementAndGet();
        String name = request.getName();
        LocalDate reservationDate = request.getDate();
        LocalTime reservationTime = request.getTime();

        reservations.add(new Reservation(id, name, reservationDate, reservationTime));
        return new ReservationResponse(id, name, reservationDate, reservationTime);
    }

    public List<Reservation> showReservations() {
        return Collections.unmodifiableList(reservations);
    }

    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservations.stream()
                .filter(r -> r.getId().equals(reservationId))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(ErrorMessage.NO_RESERVATION.getMessage()));

        reservations.remove(reservation);
    }
}
