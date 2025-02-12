package roomescape.reservation;

import java.util.List;

public interface ReservationDao {
    
    List<Reservation> findAll();
    
    Long save(Reservation reservation);
    
    void delete(Long id);
}
