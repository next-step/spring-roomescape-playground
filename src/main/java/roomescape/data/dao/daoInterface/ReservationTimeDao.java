package roomescape.data.dao.daoInterface;

import java.time.LocalTime;
import java.util.List;
import roomescape.data.entity.ReservationTime;

public interface ReservationTimeDao {

    public ReservationTime save(ReservationTime reservationTime);
    public List<ReservationTime> findAll();
    public ReservationTime findById(long id);
    public void deleteById(long id);
}
