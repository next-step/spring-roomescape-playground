package roomescape.domain.reservation.repository;

import roomescape.domain.reservation.entity.Reservation;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Reservation save(Reservation reservation) throws SQLException;

    List<Reservation> findAll();

    Optional<Reservation> findById(Long id);

    void deleteById(Long id);

}
