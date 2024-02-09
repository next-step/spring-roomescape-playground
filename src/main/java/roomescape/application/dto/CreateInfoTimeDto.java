package roomescape.application.dto;

import roomescape.domain.Time;

import java.time.LocalTime;

public class CreateInfoTimeDto {

    private final Long id;
    private final LocalTime time;

    private CreateInfoTimeDto(final Long id, final LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public static CreateInfoTimeDto from(final Time persistTime) {
        return new CreateInfoTimeDto(persistTime.getId(), persistTime.getTime());
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
