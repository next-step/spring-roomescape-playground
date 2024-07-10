package roomescape.dto;

import java.time.LocalDate;

public class ReservationResDto {
    private Long id;
    private String name;
    private LocalDate date;
    private String time;

    public ReservationResDto() {}

    public ReservationResDto(Long id, String name, LocalDate date, String time) {
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

    public LocalDate getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
