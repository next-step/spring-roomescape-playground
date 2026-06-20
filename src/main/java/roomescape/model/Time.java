package roomescape.model;

import roomescape.dto.TimeDto;

public record Time(Long id, String time) {
    public Time(long id, TimeDto timeDto) {
        this(id, timeDto.time());
    }
}
