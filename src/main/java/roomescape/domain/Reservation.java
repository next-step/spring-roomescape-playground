package roomescape.domain;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Reservation {
    private final Long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this(id, name, date, time, Clock.systemDefaultZone());
    }

    Reservation(Long id, String name, LocalDate date, LocalTime time, Clock clock) {
        validateName(name);
        validateDate(date);
        validateTime(time);
        validateReservationDateTime(date, time, clock);

        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 비어있을 수 없습니다.");
        }
    }

    private void validateDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("날짜는 비어있을 수 없습니다.");
        }
    }

    private void validateTime(LocalTime time) {
        if (time == null) {
            throw new IllegalArgumentException("시간은 비어있을 수 없습니다.");
        }
    }

    private void validateReservationDateTime(LocalDate date, LocalTime time, Clock clock) {
        LocalDateTime reservationDateTime = LocalDateTime.of(date, time).truncatedTo(ChronoUnit.MINUTES);
        LocalDateTime now = LocalDateTime.now(clock).truncatedTo(ChronoUnit.MINUTES);

        if (!reservationDateTime.isAfter(now)) {
            throw new IllegalArgumentException("예약은 현재 시각 이후여야 합니다.");
        }
    }
}
