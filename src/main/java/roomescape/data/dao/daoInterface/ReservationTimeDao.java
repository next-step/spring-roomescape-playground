package roomescape.data.dao.daoInterface;

import java.util.List;
import roomescape.data.entity.ReservationTime;

public interface ReservationTimeDao {

    public ReservationTime save(ReservationTime reservationTime);
    public List<ReservationTime> findAll();
    public void deleteById(long id);
}
