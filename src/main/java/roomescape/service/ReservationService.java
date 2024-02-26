package roomescape.service;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
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
    private final AtomicLong index = new AtomicLong(1);

    @Autowired
    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @Transactional
    public List<ReservationDto> getAllReservation() {
        List<Reservation> reservations = reservationDao.getAllReservations();
        return reservations.stream()
        .map(ReservationDto::new)
        .collect(Collectors.toList());
    }

    @Transactional
    public ReservationDto createReservation(String name, String date, String time) {

        Long newId = index.incrementAndGet();
        Reservation reservation = reservationDao.createReservation(newId, name, date, time);
        return new ReservationDto(reservation);
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
