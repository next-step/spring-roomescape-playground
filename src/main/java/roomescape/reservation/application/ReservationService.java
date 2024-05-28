package roomescape.reservation.application;

import java.util.List;

import org.springframework.stereotype.Service;

import roomescape.reservation.domain.Reservation;
import roomescape.reservation.persistence.ReservationDAO;
import roomescape.reservation.presentation.dto.request.ReservationSaveRequest;
import roomescape.reservation.presentation.dto.response.ReservationResponse;
import roomescape.reservation.presentation.exception.NotFoundReservationException;
import roomescape.time.domain.Time;
import roomescape.time.persistence.TimeDAO;

@Service
public class ReservationService {

	private final ReservationDAO reservationDAO;
	private final TimeDAO timeDAO;

	public ReservationService(ReservationDAO reservationDAO, TimeDAO timeDAO) {
		this.reservationDAO = reservationDAO;
		this.timeDAO = timeDAO;
	}

	public List<ReservationResponse> getReservations() {
		return reservationDAO.findAll().stream().map(ReservationResponse::from).toList();
	}

	public ReservationResponse saveReservation(ReservationSaveRequest request) {
		Time time = timeDAO.findById(request.timeId());
		Reservation reservation = reservationDAO.save(new Reservation(request.name(), request.date(), time));
		return ReservationResponse.from(reservation);
	}

	public void deleteReservation(Long id) {
		if (reservationDAO.findById(id) == null) {
			throw new NotFoundReservationException("존재하지 않는 예약정보 입니다.");
		}
		reservationDAO.delete(id);
	}
}
