package roomescape.domain.reservation.service;

import org.springframework.stereotype.Service;
import roomescape.domain.reservation.dto.request.ReservationCreateRequestDto;
import roomescape.domain.reservation.entity.Reservation;
import roomescape.domain.reservation.repository.ReservationDao;
import roomescape.domain.time.entity.Time;
import roomescape.domain.time.repository.TimeDao;
import roomescape.exception.NotFoundReservationException;

import java.util.List;
import java.util.Objects;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;
    private final TimeDao timeDao;

    public ReservationService(ReservationDao reservationDao, TimeDao timeDao) {
        this.reservationDao = reservationDao;
        this.timeDao = timeDao;
    }

    public List<Reservation> getReservations() {
        return reservationDao.findAllReservations();
    }

    public Reservation saveReservation(ReservationCreateRequestDto requestDto) {
        Long reservationId = reservationDao.insert(requestDto);
        Time findTime = timeDao.findTimeById(Long.parseLong(requestDto.timeId()));
        return requestDto.toEntity(reservationId, findTime);
    }

    public void deleteReservation(Long reservationId) {
        reservationDao.findAllReservations().stream()
                .filter(it -> Objects.equals(it.id(), reservationId))
                .findFirst()
                .orElseThrow(NotFoundReservationException::new);

        reservationDao.delete(reservationId);
    }
}
