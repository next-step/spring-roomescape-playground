package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationCreateForm {

    @NotBlank(message = "예약자 이름은 비워둘 수 없습니다(공백으로 채울 수 없음)")
    private String name;
    @NotNull(message = "예약날짜는 비워둘 수 없습니다")
    private LocalDate date;
    @NotNull(message = "예약시간는 비워둘 수 없습니다")
    private LocalTime time;

    public Reservation toEntity() {
        return new Reservation(null, this.name, this.date, this.time);
    }

    public ReservationCreateForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
