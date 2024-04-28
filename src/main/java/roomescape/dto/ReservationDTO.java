package roomescape.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class ReservationDTO {

    private String name;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private long timeId;

    public ReservationDTO(String name, LocalDate date, long timeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public long getTimeId() {
        return timeId;
    }
}
