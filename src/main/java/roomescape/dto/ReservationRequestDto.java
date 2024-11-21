package roomescape.dto;

import java.time.LocalDateTime;

public class ReservationRequestDto {
    private String name;
    private String date;
    private LocalDateTime time;

    public ReservationRequestDto(String name, String date, LocalDateTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
