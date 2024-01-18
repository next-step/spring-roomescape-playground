package roomescape.persistence;

import org.springframework.stereotype.Component;
import roomescape.controller.ReservationDto;
import roomescape.domain.Reservation;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class ReservationRepository {

    private final AtomicLong index = new AtomicLong(0L);

    private final List<Reservation> reservations = new CopyOnWriteArrayList<>();

    public ReservationDto save(Map<String, String> request) {
        Reservation reservation = new Reservation(
                index.incrementAndGet(),
                request.get("name"),
                request.get("date"),
                request.get("time"));
        reservations.add(reservation);

        return ReservationDto.from(reservation);
    }

    public List<ReservationDto> findAll() {
        return ReservationDto.from(reservations);
    }

    public void deleteById(long deleteId) {
        Reservation deleted = findById(deleteId).
                orElseThrow(() -> new NoSuchElementException("예약을 삭제할 수 없습니다. 잘못된 예약 id입니다."));

        reservations.remove(deleted);
    }

    public Optional<Reservation> findById(long deleteId) {
        return reservations.stream()
                .filter(reservation -> reservation.isSameId(deleteId))
                .findFirst();
    }
}
