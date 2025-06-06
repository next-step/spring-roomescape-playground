package roomescape.reservation.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservation.dto.ReservationResponseDTO;

@Service
public class ReservationService {
    public List<ReservationResponseDTO> getReservations() {
        List<ReservationResponseDTO> reservations = new ArrayList<>();
        reservations.add(new ReservationResponseDTO(1L, "브라운", LocalDate.of(2023, 1, 1),
                LocalTime.of(10, 0)));
        reservations.add(new ReservationResponseDTO(2L, "브라운", LocalDate.of(2023, 1, 2),
                LocalTime.of(11, 0)));
        reservations.add(new ReservationResponseDTO(3L, "브라운", LocalDate.of(2023, 1, 3),
                LocalTime.of(12, 0)));

        return reservations;
    }
}
