package roomescape.controller.dto;


import roomescape.domain.ReservationTime;

import java.util.List;

public record TimeDto(long id, String time) {

    public static List<TimeDto> from(List<ReservationTime> times) {
        return times.stream()
                .map(t -> new TimeDto(t.getId(), t.getTime().toString()))
                .toList();
    }
}
