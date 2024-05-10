package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.format.annotation.DateTimeFormat;

public class ReservationRequestDto {

    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;

    public ReservationRequestDto(final LocalDate date, final String name, final LocalTime time) {
        this.date = date;
        this.name = name;
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public LocalTime getTime() {
        return time;
    }

}
