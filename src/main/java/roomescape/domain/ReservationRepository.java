package roomescape.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    //By extending JpaRepository<Reservation, Long>, your ReservationRepository inherits various CRUD methods, including findAll().
    
    
    @Override
    <S extends Reservation> S save(S entity);
}
