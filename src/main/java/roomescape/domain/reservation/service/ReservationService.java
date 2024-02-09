package roomescape.domain.reservation.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.reservation.dto.request.ReservationCreateRequestDto;
import roomescape.domain.reservation.entity.Reservation;
import roomescape.domain.reservation.repository.ReservationDao;
import roomescape.domain.time.entity.Time;
import roomescape.domain.time.repository.TimeDao;
import roomescape.exception.custom.BusinessException;

import java.util.List;

import static roomescape.exception.ErrorCode.*;

@Service
@Transactional(readOnly = true)
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

    @Transactional
    public Reservation saveReservation(ReservationCreateRequestDto requestDto) {
        Long reservationId = reservationDao.insert(requestDto);
        Time findTime = timeDao.findTimeById(Long.parseLong(requestDto.timeId()))
                .orElseThrow(() -> new BusinessException(TIME_NOT_FOUND));

        return requestDto.toEntity(reservationId, findTime);
    }

    @Transactional
    public void deleteReservation(Long reservationId) {
        reservationDao.findAllReservations().stream()
                .filter(reservation -> reservation.getId().equals(reservationId))
                .findFirst()
                .orElseThrow(() -> new BusinessException(RESERVATION_NOT_FOUND));

        reservationDao.deleteReservationById(reservationId);
    }
}