package roomescape.reservation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import roomescape.reservation.dto.ReservationRequestDTO;
import roomescape.reservation.dto.ReservationResponseDTO;
import roomescape.reservation.entity.Reservation;
import roomescape.reservation.exception.NotFoundReservationException;

@Service
public class ReservationService {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    public List<ReservationResponseDTO> getReservations() {
        return reservations.stream()
                .map(Reservation::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ReservationResponseDTO addReservation(ReservationRequestDTO request) {
        Long id = index.getAndIncrement();
        Reservation newReservation = Reservation.from(request, id);
        reservations.add(newReservation);
        return newReservation.toResponseDTO();
    }

    public void deleteReservation(Long id) {
        Reservation target = reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundReservationException("해당 ID의 예약이 존재하지 않습니다: "));

        reservations.remove(target);
    }
}
