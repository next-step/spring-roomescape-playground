package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.exception.BadRequestException;
import roomescape.model.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ReservationService {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicInteger reservationId = new AtomicInteger(1);

    public void removeReservation(int id) {
        boolean removed = reservations.removeIf(reservation -> reservation.id() == id);
        if (!removed) {
            throw new BadRequestException("예외 처리 확인");
        }
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public Reservation addReservation(Reservation reservation) {
        if (reservation.name() == null || reservation.name().isBlank()
                || reservation.date() == null || reservation.date().isBlank()
                || reservation.time() == null || reservation.time().isBlank()) {
            throw new BadRequestException("입력값 null");
        }
        Reservation reservation1 = new Reservation(reservationId.getAndIncrement(), reservation.name(), reservation.date(), reservation.time());
        reservations.add(reservation1);
        return reservation1;
    }


}
