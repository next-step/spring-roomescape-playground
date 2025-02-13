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
import roomescape.repository.ReservationDAO;

@Service
public class ReservationService {
    private final AtomicLong index = new AtomicLong(0);
    private final List<Reservation> reservations = new ArrayList<>();
    private final ReservationDAO reservationDAO;

    public ReservationService(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    public List<Reservation> showReservations() {
        return reservationDAO.findReservations();
    }

    public ReservationResponse reserve(ReservationCreateRequest request) {
        Long id = index.incrementAndGet();
        String name = request.getName();
        LocalDate reservationDate = request.getDate();
        LocalTime reservationTime = request.getTime();

        reservations.add(new Reservation(id, name, reservationDate, reservationTime));
        return new ReservationResponse(id, name, reservationDate, reservationTime);
    }

    public void cancelReservation(Long reservationId) {
        Reservation targetReservation = reservations.stream()
                .filter(reservation -> reservation.isSameReservation(reservationId))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(ErrorMessage.NO_RESERVATION.getMessage()));

        reservations.remove(targetReservation);
    }
}
