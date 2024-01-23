package roomescape.dto;

import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationDto {
    @NonNull
    private LocalDate date;
    @NonNull
    private LocalTime time;
    @NonNull
    private String name;
    public LocalDate getDate() {
        return date;
    }
    public LocalTime getTime() {
        return time;
    }
    public String getName() {
        return name;
    }
}
