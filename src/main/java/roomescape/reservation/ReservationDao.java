package roomescape.reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationDao {
    
    Optional<Reservation> findById(Long id);
    
    List<Reservation> findAll();
    
    Long save(Reservation reservation);
    
    void delete(Long id);
}
