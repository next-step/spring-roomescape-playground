package roomescape.service;

import org.springframework.util.StringUtils;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.exception.ParamException;
import roomescape.exception.ReservationException;

import java.util.List;

public interface ReservationService {
    List<ReservationResponseDto> getAllReservations();
    Reservation addReservation(ReservationRequestDto reservationRequestDto);

    void deleteReservation(Long id);
}
