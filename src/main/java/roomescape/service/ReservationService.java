package roomescape.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import roomescape.entity.dto.ReservationCreateDto;
import roomescape.entity.dto.TimeCreateDto;
import roomescape.entity.Reservation;
import roomescape.entity.dao.ReservationDao;
import roomescape.entity.dao.TimeDao;
import roomescape.entity.value.Time;
import roomescape.exception.NotFoundException;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final TimeDao timeDao;

    public ReservationService(ReservationDao reservationDao, TimeDao timeDao) {
        this.reservationDao = reservationDao;
        this.timeDao = timeDao;
    }

    public Reservation saveReservation(ReservationCreateDto reservationCreateDto) {
        final Time time = findTimeById(reservationCreateDto.getTimeId());

        final Long savedId = reservationDao.save(reservationCreateDto);
        return new Reservation(savedId, reservationCreateDto.getName(), reservationCreateDto.getDate(), time);
    }

    private Time findTimeById(Long timeId) {
        return timeDao.findById(timeId)
            .orElseThrow(() -> new NotFoundException("해당 id를 가진 Time 객체를 찾을 수 없습니다."));
    }

    public List<Reservation> findAllReservations() {
        return reservationDao.findAll().stream()
            .map(reservationOutDto -> {
                Time time = findTimeById(reservationOutDto.getTimeId());
                return Reservation.of(reservationOutDto, time);
            })
            .collect(Collectors.toList());
    }

    public void deleteReservationById(Long id) {
        final int countOfDeleted = reservationDao.deleteById(id);

        if (countOfDeleted <= 0) {
            throw new NotFoundException("해당 id를 가진 예약을 찾을 수 없습니다.");
        }
    }

    public List<Time> findAllTimes() {
        return timeDao.findAll();
    }

    public Time saveTime(TimeCreateDto timeCreateDto) {
        return timeDao.save(timeCreateDto);
    }

    public void deleteTimeById(Long id) {
        final int countOfDeleted = timeDao.deleteById(id);

        if (countOfDeleted <= 0) {
            throw new NotFoundException("해당 id를 가진 Time 객체를 찾을 수 없습니다.");
        }
    }

}
