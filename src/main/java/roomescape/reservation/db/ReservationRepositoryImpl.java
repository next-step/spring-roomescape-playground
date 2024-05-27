package roomescape.reservation.db;

import java.util.List;

public interface ReservationRepositoryImpl {

    List<ReservationEntity> findAll();

    ReservationEntity save(ReservationEntity reservationEntity);

    ReservationEntity findById(Long id);

    void deleteById(Long id);
}
