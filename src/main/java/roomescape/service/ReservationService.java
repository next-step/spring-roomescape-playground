package roomescape.service;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.web.dao.ReservationDao;
import roomescape.web.dto.ReservationDto;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.Optional;


@Service
public class ReservationService {

    // private List<Reservation> reservations = new ArrayList<>();
    private final ReservationDao reservationDao;
    private final TimeService timeService;
    private final AtomicLong index = new AtomicLong(0);

    @Autowired
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
    public ReservationDto createReservation(String name, String date, Long timeId) {

        Optional<Time> optionalTime = timeService.getTimeById(timeId);

        if(optionalTime.isPresent()) {
            Time time = optionalTime.get();
            Long newId = index.incrementAndGet();
            Reservation reservation = reservationDao.createReservation(newId, name, date, time);
            return new ReservationDto(reservation);
        } else {
            throw new RuntimeException("an error occurred during reservation creation.");
        }
    }

    @Transactional
    public void deleteReservationById(Long id) {
        reservationDao.deleteReservationById(id);
    }

    @Transactional
    public Optional<Reservation> getReservationById(Long id) {
        return reservationDao.getReservationById(id);

    }
}
