package roomescape.data.service;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.data.repository.repositoryInterface.ReservationRepository;
import roomescape.data.dto.ReservationRequest;
import roomescape.data.dto.ReservationResponse;
import roomescape.data.entity.Reservation;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationDao;

    public List<ReservationResponse> getReservations() {
        List<Reservation> reservations = reservationDao.findAll();
        return reservations.stream().map(ReservationResponse::from).collect(Collectors.toList());
    }

    public ReservationResponse createReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation newReservation = reservationDao.save(reservationRequest);
        return ReservationResponse.from(newReservation);
    }

    public void deleteReservation(@PathVariable Long deletedReservationId) {
        reservationDao.deleteById(deletedReservationId);
    }
}
