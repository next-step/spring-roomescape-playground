package roomescape.service;

import jakarta.annotation.PostConstruct;
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

    @PostConstruct
    public void init() {
        addReservation(new Reservation(1, "브라운", "2024-06-23", "16:00"));
        addReservation(new Reservation(2, "브라운", "2024-06-23", "16:00"));
    }

    public void removeReservation(int id) {
        boolean removed = reservations.removeIf(reservation -> reservation.id() == id);
        if (!removed) {
            throw new BadRequestException("요청한 id에 해당하는 예약이 존재하지 않습니다");
        }
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public Reservation addReservation(Reservation reservation) {
        verification(reservation);
        Reservation reservation1 = new Reservation
                (reservationId.getAndIncrement(), reservation.name(), reservation.date(), reservation.time());
        reservations.add(reservation1);
        return reservation1;
    }

    // 검증 메서드 분리
    private void verification(Reservation reservation) {
        if (reservation.name() == null || reservation.name().isBlank()
                || reservation.date() == null || reservation.date().isBlank()
                || reservation.time() == null || reservation.time().isBlank()) {
            throw new BadRequestException("입력값 null 이거나 빈 값 입니다 ");
        }
    }
}
