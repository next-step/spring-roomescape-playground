package roomescape.reservation.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;

@Data
public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    private Reservation(Long id, String name, LocalDate date, LocalTime time) {
        validate(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation from(ReservationRequest dto, Long id) {
        return new Reservation(id, dto.name(), dto.date(), dto.time());
    }

    private void validate(String name, LocalDate date, LocalTime time) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("유효한 이름이 아닙니다.");
        }
        if (date == null || date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("유효한 날짜 값이 아닙니다.");
        }
        if (date.equals(LocalDate.now()) && time.isBefore(LocalTime.now())) {
            throw new IllegalArgumentException("현재 시간보다 이전 시간으로는 예약할 수 없습니다.");
        }
        if (time == null) {
            throw new IllegalArgumentException("유효한 시간이 아닙니다.");
        }
    }

    public ReservationResponse toResponseDTO() {
        return new ReservationResponse(id, name, date, time);
    }
}
