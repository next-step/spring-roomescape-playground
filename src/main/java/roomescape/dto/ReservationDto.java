package roomescape.dto;

import roomescape.domain.Time;

public record ReservationDto(
        String name, String date, Time time
) {
    public static ReservationDto from(String name, String date, Time time) {
        return new ReservationDto(name, date, time);
    }
}

