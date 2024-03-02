package roomescape.service;

import java.util.List;
import roomescape.dto.reservation.ReservationRequestDTO.AddReservationRequest;
import roomescape.dto.reservation.ReservationResponseDTO.AddReservationResponse;
import roomescape.dto.reservation.ReservationResponseDTO.QueryReservationResponse;

public interface ReservationService {

	public List<QueryReservationResponse> getReservations();

	public AddReservationResponse addReservation(AddReservationRequest reservationRequest);

	public void deleteReservation(Long id);
}