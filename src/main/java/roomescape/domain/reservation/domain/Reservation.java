package roomescape.domain.reservation.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Reservation {
    private Long id;
    @NotBlank(message = "이름형식을 확인해주세요.")
    private String name;
    @NotNull(message = "날짜형식을 확인해주세요.")
    private LocalDate date;
    @NotNull(message = "시간형식을 확인해주세요.")
    private LocalTime time;

    public Reservation() {

    }

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }


    public Long getId() { return id; }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public static Reservation toEntity(Reservation reservation, Long id) {
        return new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getTime());
    }
}
