package roomescape.Service;

import roomescape.DTO.ReservationRequestDTO;
import roomescape.DTO.ReservationResponseDTO;
import roomescape.Domain.Reservation;

import java.util.List;

public interface ReservationService {
    public List<ReservationResponseDTO> findAllReservations();

    public ReservationResponseDTO findReservationById(Long id);

    public Long createReservation(ReservationRequestDTO reservationRequest);

    public void deleteReservationById(Long id);
}
