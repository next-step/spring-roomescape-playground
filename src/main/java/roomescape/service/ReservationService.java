package roomescape.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import roomescape.domain.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


@Service
public class ReservationService {
    private List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

//    public List<ReserveListResponseDto> converToDtoList(List<Reservation> reservations) {
//        return reservations.stream()
//                .map(ReserveListResponseDto::new)
//                .collect(Collectors.toList());
//    }

    @Transactional
    public List<Reservation> getAllReservation() {
        return reservations;
    }

    @Transactional
    public Reservation createReservation(String name, String date, String time) {
        Long reservationId = index.getAndIncrement();
        Reservation reservation = new Reservation(reservationId, name, date, time);
        reservations.add(reservation);
        return reservation;
    }

    @Transactional
    public void deleteReservationById(@PathVariable Long id) {
        reservations.removeIf(reservation -> reservation.getId().equals(id));
    }

    public Reservation getReservationById(Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
