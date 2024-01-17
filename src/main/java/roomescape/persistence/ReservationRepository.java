package roomescape.persistence;

import org.springframework.stereotype.Component;
import roomescape.controller.ReservationDto;
import roomescape.domain.Reservation;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class ReservationRepository {

    private final AtomicLong index = new AtomicLong(0L);

    private final List<Reservation> reservations;

    public ReservationRepository(List<Reservation> reservations) {
        this.reservations = reservations;
    }

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
        try {
            reservations.remove(Long.valueOf(deleteId).intValue() - 1);
        } catch (Exception e) {
            throw new IllegalArgumentException("예약을 삭제할 수 없습니다. 잘못된 예약 id입니다.");
        }
    }
}
