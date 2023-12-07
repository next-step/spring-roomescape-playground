package roomescape.room;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ReservationService {

	private final ReservationDAO reservationDAO;

	public ReservationService(ReservationDAO reservationDAO) {
		this.reservationDAO = reservationDAO;
	}

	@Transactional
	public ReservationResponse.Create createReservation(ReservationRequest.Create request) {
		Long savedId = reservationDAO.save(request.toEntity());
		return ReservationResponse.Create.fromEntity(savedId);
	}

	public ReservationResponse.Read getReservation(Long id) {
		Reservation reservation = reservationDAO.findById(id);
		return ReservationResponse.Read.fromEntity(reservation);
	}

	public void deleteReservationById(Long id) {
		reservationDAO.deleteById(id);
	}

	@Transactional
	public List<ReservationResponse.Read> getReservations() {
		List<Reservation> reservations = reservationDAO.findAll();
		return ReservationResponse.Read.fromEntity(reservations);
	}
}
