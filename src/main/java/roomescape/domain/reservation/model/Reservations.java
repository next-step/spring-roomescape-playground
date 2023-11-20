package roomescape.domain.reservation.model;

import static java.util.Collections.unmodifiableList;
import static roomescape.global.error.exception.ReservationException.ErrorCode.DUPLICATED;
import static roomescape.global.error.exception.ReservationException.ErrorCode.NOT_FOUND;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import lombok.Getter;
import org.springframework.stereotype.Component;
import roomescape.global.error.exception.ReservationException;

@Component
public class Reservations {
    private static final AtomicLong INDEX = new AtomicLong(0);

    @Getter
    private final List<Reservation> reservations = new ArrayList<>();

    public Reservation save(String name, LocalDate date, LocalTime time) {
        Reservation reservation = generateReservation(name, date, time);
        validateDuplicate(reservation);
        reservations.add(reservation);
        return reservation;
    }

    private Reservation generateReservation(String name, LocalDate date, LocalTime time) {
        return Reservation.builder()
                .id(INDEX.incrementAndGet())
                .name(name)
                .date(date)
                .time(time)
                .build();
    }

    private void validateDuplicate(Reservation reservation) {
        boolean isDuplicate = reservations.stream().anyMatch(r -> r.isNameEquals(reservation));
        if (isDuplicate) {
            throw new ReservationException(DUPLICATED);
        }
    }

    public Reservation findById(long reservationId) {
        return reservations.stream()
                .filter(reservation -> reservation.isIdEquals(reservationId))
                .findFirst()
                .orElseThrow(() -> new ReservationException(NOT_FOUND));
    }

    public void delete(Reservation reservation) {
        reservations.remove(reservation);
    }

    public void deleteById(long reservationId) {
        Reservation reservation = findById(reservationId);
        delete(reservation);
    }

    public void clear() {
        INDEX.set(0);
        reservations.clear();
    }

    public List<Reservation> get() {
        return unmodifiableList(reservations);
    }
}
