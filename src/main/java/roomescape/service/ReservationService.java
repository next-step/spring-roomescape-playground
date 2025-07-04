package roomescape.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.controller.dto.RequestReservation;
import roomescape.dao.ReservationDao;
import roomescape.dao.TimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.global.exception.NotFoundException;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final TimeDao timeDao;

    public ReservationService(final ReservationDao reservationDao, final TimeDao timeDao) {
        this.reservationDao = reservationDao;
        this.timeDao = timeDao;
    }

    @Transactional
    public Reservation create(final RequestReservation requestReservation) {
        Time time = timeDao.findById(requestReservation.getTimeId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 시간이에요."));

        validatePasted(requestReservation.getDate(), time);
        return reservationDao.save(requestReservation.getName(), requestReservation.getDate(), time);
    }

    @Transactional(readOnly = true)
    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    private Reservation findById(final Long id) {
        return reservationDao.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 예약이에요."));
    }

    @Transactional
    public void delete(final Long id) {
        Reservation reservation = findById(id);
        reservationDao.deleteById(reservation.id());
    }

    private void validatePasted(final LocalDate date, final Time time) {
        if (LocalDateTime.of(date, time.time()).isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("이미 지난 날짜 및 시간은 예약할 수 없어요.");
        }
    }
}
