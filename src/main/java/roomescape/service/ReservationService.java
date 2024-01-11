package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.controller.ReservationDto;
import roomescape.domain.Reservation;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ReservationService {

    private static AtomicLong index = new AtomicLong(0L);

    private List<Reservation> reservations;

    public ReservationService(List<Reservation> reservations) {
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
        return ReservationDto.of(reservations);
    }

    public void deleteById(long deleteId) {
        reservations.remove(Long.valueOf(deleteId).intValue() - 1);
    }
}
