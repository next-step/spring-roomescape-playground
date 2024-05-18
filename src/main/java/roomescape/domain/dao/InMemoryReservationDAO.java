package roomescape.domain.dao;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.controller.exception.NotFoundException;
import roomescape.domain.Reservation;

@Repository
public class InMemoryReservationDAO implements ReservationDAO {
    private final String Not_Found_Reservation_Announcement = "삭제할 예약 정보가 없습니다.";
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @Override
    public List<Reservation> findAll() {
        return reservations;
    }

    @Override
    public Reservation insert(Reservation reservation) {
        reservation.setId(index.getAndIncrement());
        reservations.add(reservation);
        return reservation;
    }

    @Override
    public void deleteById(long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(Not_Found_Reservation_Announcement));
        reservations.remove(reservation);
    }


}
