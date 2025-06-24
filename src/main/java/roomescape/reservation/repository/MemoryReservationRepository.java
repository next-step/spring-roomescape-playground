package roomescape.reservation.repository;

import roomescape.exception.BadRequestException;
import roomescape.reservation.domain.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryReservationRepository implements ReservationRepository{
    private final AtomicLong index = new AtomicLong(1);
    private final List<Reservation> reservations = new ArrayList<>();

    @Override
    public List<Reservation> findAll() {
        return List.copyOf(reservations);
    }

    @Override
    public Reservation save(Reservation reservation) {
        Reservation newReservation = new Reservation(index.getAndIncrement(), reservation.name(), reservation.date(), reservation.time());
        reservations.add(newReservation);
        return newReservation;
    }

    @Override
    public void deleteById(Long id) {
        Reservation reservation = reservations.stream()
                .filter(reservationItem -> Objects.equals(reservationItem.id(), id))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("삭제할 예약이 존재하지 않습니다."));
        reservations.remove(reservation);
    }
}
