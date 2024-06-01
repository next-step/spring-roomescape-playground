package roomescape.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import roomescape.domain.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
