package roomescape.repository;

import roomescape.domain.ReservationEntity;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    List<ReservationEntity> findAll();

    Optional<ReservationEntity> findById(Long id);

    ReservationEntity save(ReservationEntity reservation);

    void deleteById(Long id);
}
