package roomescape.dto;

import java.time.LocalDate;

public class ReservationReqDto {
    private String name;
    private LocalDate date;
    private String time;

    public ReservationReqDto() {}

    public ReservationReqDto(String name, LocalDate date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
