package roomescape.repository;

import roomescape.domain.Reservation;
import roomescape.domain.dto.CheckReservationsResponse;
import roomescape.domain.dto.RequestReservation;

import java.util.List;

public interface ReservationRepository {

    Reservation addReservation(RequestReservation request);

    List<CheckReservationsResponse> findAll();

    void deleteReservationById(Long id);
}
