package roomescape.dto;

import roomescape.domain.Time;

public class ReservationResponseDto {
    private Long id;
    private String name;
    private String date;
    private Time time;

    public ReservationResponseDto(Long id, String name, String date, Time time) {
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

    public Time getTime() {
        return time;
    }
}
