package roomescape.repository;

import roomescape.dto.ReservationDTO;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    List<ReservationDTO> findAll();

    Optional<ReservationDTO> findById(Long id);

    ReservationDTO save(ReservationDTO reservation);

    void deleteById(Long id);
}
