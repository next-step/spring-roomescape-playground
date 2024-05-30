package roomescape.repository;

import roomescape.domain.ReservationDomain;

import java.util.List;

public interface ReservationRepository {


    List<ReservationDomain> findAll();

    ReservationDomain save(ReservationDomain reservation);

    void deleteById(Long id);

}
