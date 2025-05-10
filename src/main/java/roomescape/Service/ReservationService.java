package roomescape.Service;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.Domain.Reservation;
import roomescape.Exception.InvalidReservationException;
import roomescape.Exception.NotFoundReservationException;

@Service
public class ReservationService {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    public Reservation add(String name, LocalDate date, LocalTime time) {
        Reservation reservation = new Reservation(
                (int) index.getAndIncrement(),
                name,
                date,
                time
        );

        boolean isDuplicate = reservations.stream().anyMatch(r ->
                r.getName().equals(name) &&
                        r.getDate().equals(date) &&
                        r.getTime().equals(time));

        if (isDuplicate) {
            throw new InvalidReservationException("동일한 예약이 이미 존재합니다.");
        }

        reservations.add(reservation);
        return reservation;
    }

    public List<Reservation> findAll() {
        return reservations;
    }

    public void delete(int id) {
        boolean removed = reservations.removeIf(r -> r.getId() == id);
        if (!removed) {
            throw new NotFoundReservationException("해당 ID가 없습니다.");
        }
    }
}
