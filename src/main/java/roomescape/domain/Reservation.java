package roomescape.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private Time time;

    public Reservation(Long id, String name, LocalDate date, Time time) {
        validateName(name);
        validateDateTime(date, time);

        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 필수 입력값입니다.");
        }
    }

    private void validateDateTime(LocalDate date, Time time) {
        Objects.requireNonNull(date, "날짜는 필수 입력값입니다.");
        Objects.requireNonNull(time, "시간은 필수 입력값입니다.");

        LocalDateTime reservationDateTime = LocalDateTime.of(date, time.getTime());
        if (reservationDateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("지나간 시간은 예약할 수 없습니다.");
        }
    }
}
