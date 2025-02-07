package roomescape.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
        return reservations;
    }

    public void cancelReservation(Long userId) {
        for (Reservation reservation : reservations) {
            if (reservation.getId().equals(userId)) {
                reservations.remove(reservation);
                return;
            }
        }
        throw new InvalidValueException(ErrorMessage.NO_RESERVATION.getMessage());
    }
}
