package roomescape.service;

import java.util.List;

import roomescape.controller.dto.ReservationCreate;
import roomescape.controller.dto.ReservationResponse;
import roomescape.domain.Reservation;

public interface ReservationService {
    List<ReservationResponse> findAll();

    ReservationResponse add(ReservationCreate request);

    void remove(Long id);
}
