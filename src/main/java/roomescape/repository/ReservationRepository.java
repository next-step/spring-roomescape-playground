package roomescape.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import roomescape.domain.Reservation;

import java.util.List;

public interface ReservationRepository {


    List<Reservation> findAll();

    Reservation save(Reservation reservation);

    void deleteById(Long id);

}
