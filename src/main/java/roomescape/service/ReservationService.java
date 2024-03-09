package roomescape.service;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.web.dao.ReservationDao;
import roomescape.web.dto.CreateReservationRequestDto;
import roomescape.web.dto.ReservationDto;
import roomescape.web.exception.NotFoundReservationException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import javax.validation.Valid;



@Service
public class ReservationService {


    private final ReservationDao reservationDao;
    private final TimeService timeService;
    private final AtomicLong index = new AtomicLong(0);

    public ReservationService(ReservationDao reservationDao, TimeService timeService) {
        this.reservationDao = reservationDao;
        this.timeService = timeService;
    }

    @Transactional
    public List<ReservationDto> getAllReservation() {
        List<Reservation> reservations = reservationDao.getAllReservations();
        return reservations.stream()
        .map(ReservationDto::new)
        .collect(Collectors.toList());
    }

    @Transactional
    public Reservation createReservation(@Valid CreateReservationRequestDto requestDto) {

        Optional<Time> optionalTime = timeService.getTimeById(requestDto.getTimeId());

        if (!optionalTime.isPresent()) {
            throw new NotFoundReservationException("필요한 인자가 부족합니다.");
        }

        Time time = optionalTime.get();
        Long newId = index.incrementAndGet();
        Reservation reservation = reservationDao.createReservation(newId, requestDto.getName(), requestDto.getDate(), time);
        return reservation;
    };

    @Transactional
    public void deleteReservationById(Long id) {
        reservationDao.deleteReservationById(id);
    }

    @Transactional
    public Optional<Reservation> getReservationById(Long id) {
        return reservationDao.getReservationById(id);
    }
};
