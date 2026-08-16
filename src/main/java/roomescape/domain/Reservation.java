package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public Reservation (Long id, String name, LocalDate date, LocalTime time){
        validateName(name);
        validateDate(date);
        validateTime(time);

        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public static Reservation toEntity(ReservationRequest request, Long id) {
        return new Reservation(id, request.getName(), request.getDate(), request.getTime());
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름을 입력해주세요.");
        }
    }

    private void validateDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("날짜를 입력해주세요.");
        }
    }

    private void validateTime(LocalTime time) {
        if (time == null) {
            throw new IllegalArgumentException("시간을 입력해주세요.");
        }
    }
}
