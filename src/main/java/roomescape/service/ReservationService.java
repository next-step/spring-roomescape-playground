package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationValidator;
import roomescape.dto.ReservationRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ReservationService {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);
    private final ReservationValidator validator;

    public ReservationService(ReservationValidator validator) {
        this.validator = validator;
    }

    public List<Reservation> getAllReservations() {
        return reservations;
    }

    public Reservation createReservation(ReservationRequest request) {
        validator.validateReservationTime(request);
        validator.validateDuplicatedReservation(request, reservations);

        Reservation newReservation = new Reservation(
                index.incrementAndGet(),
                request.getName(),
                request.getDate(),
                request.getTime()
        );
        reservations.add(newReservation);
        return newReservation;
    }

    public void deleteReservation(Long id) {
        boolean isRemoved = reservations.removeIf(reservation -> reservation.getId().equals(id));
        if (!isRemoved) {
            throw new IllegalArgumentException("존재하지 않는 예약입니다.");
        }
    }
}
