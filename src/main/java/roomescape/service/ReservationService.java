package roomescape.service;

import static io.micrometer.common.util.StringUtils.isBlank;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.reservation.ReservationRequest;
import roomescape.dto.time.TimeRequest;
import roomescape.exception.InvalidReservationException;
import roomescape.exception.ReservationNotFoundException;
import roomescape.exception.TimeNotFoundException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeDao;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final TimeDao timeDao;

    public ReservationService(ReservationRepository reservationRepository, TimeDao timeDao) {
        this.reservationRepository = reservationRepository;
        this.timeDao = timeDao;
    }

    public Long saveReservation(ReservationRequest reservationRequest) {
        if(isBlank(reservationRequest.getName()) || reservationRequest.getDate() == null || reservationRequest.getTime() == null) {
            throw new InvalidReservationException("이름, 날짜, 시간은 입력해야합니다.");
        }
        Reservation reservation = new Reservation(null,
                reservationRequest.getName(),
                reservationRequest.getDate(),
                reservationRequest.getTime());
        return reservationRepository.saveReservation(reservation);
    }

    public Reservation findReservationById(Long id) {
        return reservationRepository.findReservationById(id);
    }

    public List<Reservation> findAllReservation() {
        return reservationRepository.findAllReservation();
    }

    public void deleteReservationById(Long id) {
        try {
            reservationRepository.findReservationById(id);
            reservationRepository.deleteReservationById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ReservationNotFoundException("해당 id의 예약을 찾을 수 없습니다");
        }
    }

    public Time findTimeById(Long id) {
        return timeDao.findTimeById(id);
    }

    public Long saveTime(TimeRequest timeRequest) {
        if(timeRequest.getTime() == null) {
            throw new InvalidReservationException("");
        }
        Time time = new Time(
                null,
                timeRequest.getTime()
        );
        return timeDao.saveTime(time);
    }

    public void deleteTime(Long id) {
        try {
            timeDao.findTimeById(id);
            timeDao.deleteTimeById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new TimeNotFoundException("헤당 시간을 찾을 수 없습니다");
        }
    }
}
