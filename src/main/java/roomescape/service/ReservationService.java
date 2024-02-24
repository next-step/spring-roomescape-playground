package roomescape.service;

import java.util.List;
import roomescape.dto.ReservationRequestDTO.AddReservationRequest;
import roomescape.dto.ReservationResponseDTO.AddReservationResponse;
import roomescape.dto.ReservationResponseDTO.QueryReservationResponse;

public interface ReservationService {

	public List<QueryReservationResponse> getReservations();

	public AddReservationResponse addReservation(AddReservationRequest reservationRequest);

	public void deleteReservation(Long id);
}
