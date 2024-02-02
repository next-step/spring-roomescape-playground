package roomescape.dto;

import roomescape.domain.Time;

public record TimeRequestDto(String time) {

    public static TimeRequestDto convertToDto(Time time) {
        return new TimeRequestDto(time.getTime());
    }

    public Time toEntity(long id) {
        return new Time(id, time);
    }
}
