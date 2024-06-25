package roomescape.domain.reservation.dao;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.controller.exception.NotFoundException;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.dto.ResponseReservationDTO;
import roomescape.domain.reservation.dto.ReservationDTO;

@Repository
public class InMemoryReservationDAO implements ReservationDAO {
    private final String Not_Found_Reservation_Announcement = "삭제할 예약 정보가 없습니다.";
    private final List<ReservationDTO> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @Override
    public List<ReservationDTO> findAll() {
        return reservations;
    }

    @Override
    public ResponseReservationDTO insert(Reservation reservation) {
        reservation.setId(index.getAndIncrement());
        ReservationDTO newReservation = reservation.toReservationDTO();
        reservations.add(newReservation);
        return ResponseReservationDTO.toResponseReservation(newReservation);
    }

    @Override
    public void deleteById(long id) {
        ReservationDTO reservation = reservations.stream()
                .filter(it -> Objects.equals(it.id(), id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(Not_Found_Reservation_Announcement));
        reservations.remove(reservation);
    }
}
