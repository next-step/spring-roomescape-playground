package roomescape.domain;

import java.util.List;

public interface ReservationRepository {

    Reservation save(final Reservation reservation);

    List<Reservation> findAll();

    void delete(final Long id);

    Reservation findById(final Long id);
}
