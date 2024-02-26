package roomescape.dao;

import java.util.List;

import roomescape.domain.Reservation;

public interface ReservationDao {

    List<Reservation> readAll();

    Reservation read(Long id);

    Reservation create(Reservation reservation);

    void delete(Long id);
}
