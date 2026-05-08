package roomescape;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.exception.InvalidReservationException;
import roomescape.exception.NotFoundReservationException;

public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        validate(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validate(String name, LocalDate date, LocalTime time) {
        if (name == null || name.isBlank() || date == null || time == null) {
            throw new InvalidReservationException("필수 값이 누락되었습니다.");
        }

        if (date.isBefore(LocalDate.now())) {
            throw new InvalidReservationException("과거 날짜로 예약할 수 없습니다.");
        }

        if (date.isEqual(LocalDate.now()) && time.isBefore(LocalTime.now())) {
            throw new InvalidReservationException("이미 지난 시간입니다.");
        }
    }

    public static Reservation toEntity(Reservation reservation, Long id) {
        return new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public boolean isSameTime(LocalDate date, LocalTime time) {
        return this.date.equals(date) && this.time.equals(time);
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
}
