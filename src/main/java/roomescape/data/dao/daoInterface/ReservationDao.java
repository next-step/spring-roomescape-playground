package roomescape.data.dao.daoInterface;

import java.util.List;
import roomescape.data.dto.ReservationRequest;
import roomescape.data.dto.ReservationResponse;

public interface ReservationDao {

    public List<ReservationResponse> getReservations();

    public long createReservation(ReservationRequest reservationRequest);

    public void deleteReservation(Long id);
}
