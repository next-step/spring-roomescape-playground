package roomescape.room;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
	private final ReservationDAO reservationDAO;

	public ReservationService(ReservationDAO reservationDAO) {
		this.reservationDAO = reservationDAO;
	}

	public ReservationResponse.Create createReservation(ReservationRequest.Create request) {
		Long savedId = reservationDAO.save(request.toEntity());
		return ReservationResponse.Create.toDTO(savedId);
	}

	public ReservationResponse.Read getReservation(Long id) {
		Reservation reservation = reservationDAO.findById(id);
		return ReservationResponse.Read.toDTO(reservation);
	}

	public void deleteReservationById(Long id) {
		reservationDAO.deleteById(id);
	}

	public List<ReservationResponse.Read> getReservations() {
		List<Reservation> reservations = reservationDAO.findAll();
		return ReservationResponse.Read.toDTO(reservations);
	}
}
