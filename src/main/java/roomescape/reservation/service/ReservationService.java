package roomescape.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservation.domain.Reservation;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReservationService {

    private final List<Reservation> reservations = new ArrayList<>();

    public List<Reservation> findReservations() {
        return reservations;
    }
}
