package roomescape.reservation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import roomescape.reservation.dto.ReservationRequestDTO;
import roomescape.reservation.dto.ReservationResponseDTO;
import roomescape.reservation.entity.Reservation;

@Service
public class ReservationService {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);

    public List<ReservationResponseDTO> getReservations() {
        return reservations.stream()
                .map(Reservation::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ReservationResponseDTO addReservation(ReservationRequestDTO request) {
        Long id = index.incrementAndGet();
        Reservation newReservation = Reservation.from(request, id);
        reservations.add(newReservation);
        return newReservation.toResponseDTO();
    }

    public void deleteReservation(Long id) {
        reservations.removeIf(reservation -> reservation.getId().equals(id));
    }
}
