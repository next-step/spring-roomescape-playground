package roomescape.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.reservation.dto.response.GetReservationResponse;
import roomescape.domain.reservation.entity.Reservation;
import roomescape.domain.time.entity.Time;
import roomescape.domain.reservation.repository.ReservationRepositoryJdbc;
import roomescape.domain.time.repository.TimesRepositoryJdbc;
import roomescape.global.BusinessException;

import java.time.LocalDate;
import java.util.List;

import static roomescape.global.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepositoryJdbc reservationRepositoryJdbc;
    private final TimesRepositoryJdbc timeRepositoryJdbc;

    public List<GetReservationResponse> findAllReservations() {
        return reservationRepositoryJdbc.findAll()
                .stream()
                .map(GetReservationResponse::from)
                .toList();
    }

    @Transactional
    public Reservation saveReservation(final String name, final LocalDate date, final Long timeId) {
        Time findTime = timeRepositoryJdbc.findById(timeId)
                .orElseThrow(() -> new BusinessException(timeId, "timeId", TIME_NOT_FOUND));

        Reservation reservation = Reservation.builder()
                .name(name)
                .date(date)
                .time(findTime)
                .build();

        return reservationRepositoryJdbc.save(reservation);
    }

    public Reservation findReservation(final Long id) {
        return reservationRepositoryJdbc.findById(id)
                .orElseThrow(() -> new BusinessException(id, "reservationId", RESERVATION_NOT_FOUND));
    }

    @Transactional
    public void deleteReservation(final Long id) {
        reservationRepositoryJdbc.deleteById(id);
    }


}
