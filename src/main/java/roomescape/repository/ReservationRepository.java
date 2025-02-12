package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;

import java.util.List;

@Repository
public interface ReservationRepository {

    List<Reservation> findAll();
}
