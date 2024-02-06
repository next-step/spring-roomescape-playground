package roomescape.dto;

import roomescape.domain.Time;

public class CreateReservationResponseDto {
    private final Long reservationId;
    private final String name;
    private final String date;
    private final String time;

    public Long getReservationId() { return this.reservationId;}
    public String getName() {
        return this.name;
    }
    public String getDate() {
        return this.date;
    }
    public String getTime() {
        return this.time;
    }

    public CreateReservationResponseDto(Long reservationId, String name, String date, String time) {
        this.reservationId = reservationId;
        this.name = name;
        this.date = date;
        this.time = time;
    }

}
