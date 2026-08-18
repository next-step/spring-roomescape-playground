package roomescape;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private Long id;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate date;
    @JsonFormat(pattern = "HH:mm")
    LocalTime time;

    public Reservation() {
    }

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        validateReservationArgument(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(String name, LocalDate date, LocalTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation toEntity(Reservation reservation, Long id) {
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }

    public void validateReservationArgument(String name, LocalDate date, LocalTime time) {
        LocalDate todayDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 비워져있을 수 없습니다.");
        }
        if (date == null) {
            throw new IllegalArgumentException("날짜는 비워져있을 수 없습니다.");
        }
        if (date.isBefore(todayDate)) {
            throw new IllegalArgumentException("지난 날짜를 예약할 수 없습니다.");
        }
        if (time == null) {
            throw new IllegalArgumentException("시간은 비워져있을 수 없습니다.");
        }
        if (date.isEqual(todayDate) && time.isBefore(nowTime)) {
            throw new IllegalArgumentException("지난 시간을 예약할 수 없습니다.");
        }
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

