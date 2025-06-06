package roomescape.reservation.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import roomescape.reservation.dto.ReservationRequestDTO;
import roomescape.reservation.dto.ReservationResponseDTO;

@Data
@AllArgsConstructor
public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public static Reservation from(ReservationRequestDTO dto, Long id) {
        return new Reservation(id, dto.name(), dto.date(), dto.time());
    }

    public ReservationResponseDTO toResponseDTO() {
        return new ReservationResponseDTO(id, name, date, time);
    }
}
