package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ReservationResponseDto {
    private Long id;
    private String name;
    private String date;
    private LocalTime time;

    public ReservationResponseDto(Long id, String name, String date, LocalTime time) {
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

    public String getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
